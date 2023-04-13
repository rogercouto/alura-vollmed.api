package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdvanceHoursValidator implements AppointmentScheduleValidator {

    private static final int ADVANCE_MINUTES = 30;

    @Override
    public void validate(AppointmentScheduleData data) throws DataValidationException {
        var now = LocalDateTime.now();
        var isBeforeAdvance = data.dateTime().isBefore(now.plusMinutes(ADVANCE_MINUTES));
        if (isBeforeAdvance) {
            throw new DataValidationException("Appointment must be scheduled at least 30 minutes in advance");
        }
    }
}
