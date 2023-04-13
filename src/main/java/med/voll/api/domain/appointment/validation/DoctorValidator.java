package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;
import med.voll.api.repository.AppointmentRepository;
import med.voll.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class DoctorValidator implements AppointmentScheduleValidator{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void validate(AppointmentScheduleData data) throws DataValidationException {
        if (data.doctorId() != null) {
            var doctor = doctorRepository.findByIdAndActiveTrue(data.doctorId());
            if (doctor == null) {
                throw new DataValidationException("Doctor not exists or is inactive");
            }
            var appointment = appointmentRepository.findByDoctorAndDateTime(doctor, data.dateTime().truncatedTo(HOURS));
            if (appointment != null) {
                throw new DataValidationException("Doctor is not available at this time");
            }
        } else {
            if (data.speciality() == null) {
                throw new DataValidationException("Doctor id or speciality must be informed");
            }
        }
    }

}
