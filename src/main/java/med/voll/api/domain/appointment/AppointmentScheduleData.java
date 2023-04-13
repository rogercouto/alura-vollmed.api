package med.voll.api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.doctor.SpecialtyData;

import java.time.LocalDateTime;

public record AppointmentScheduleData(
    Long doctorId,
    @NotNull Long patientId,
    @NotNull @Future LocalDateTime dateTime,
    SpecialtyData speciality
) {
}
