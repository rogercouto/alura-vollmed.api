package med.voll.api.domain.appointment;

import med.voll.api.domain.doctor.DoctorDetailData;
import med.voll.api.domain.patient.PatientDetailData;

import java.time.LocalDateTime;

public record AppointmentDetailData(
        Long id,
        DoctorDetailData doctor,
        PatientDetailData patient,
        LocalDateTime dateTime
) {
    public AppointmentDetailData(Appointment appointment) {
        this(
            appointment.getId(),
            new DoctorDetailData(appointment.getDoctor()),
            new PatientDetailData(appointment.getPatient()),
            appointment.getDateTime()
        );
    }

}
