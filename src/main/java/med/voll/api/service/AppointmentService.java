package med.voll.api.service;

import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.validation.AppointmentScheduleValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.infra.exception.DataValidationException;
import med.voll.api.repository.AppointmentRepository;
import med.voll.api.repository.DoctorRepository;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
public class AppointmentService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private List<AppointmentScheduleValidator> validators;

    public Appointment schedule(AppointmentScheduleData data) throws DataValidationException {
        for (var validator : validators) {
            validator.validate(data);
        }

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);

        var appointment = new Appointment(null, doctor, patient, data.dateTime().truncatedTo(HOURS));
        return appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor(AppointmentScheduleData data) throws DataValidationException {
        if (data.doctorId() != null) {
            if (data.speciality() != null) {
                throw new DataValidationException("doctor id and speciality cannot be informed together");
            }
            var doctor = doctorRepository.getReferenceById(data.doctorId());
            if (!doctor.isActive()) {
                throw new DataValidationException(String.format("doctor with id %d not exists or is inactive", doctor.getId()));
            }
            return doctor;
        }
        if (data.speciality() == null) {
            throw new DataValidationException("doctor id or speciality must be informed");
        }

        var doctor = doctorRepository.chooseRandomDoctorOnDate(Specialty.fromRequest(data.speciality()), data.dateTime());

        if (doctor == null) {
            throw new DataValidationException("no doctor available for this speciality");
        }

        return doctor;
    }
}
