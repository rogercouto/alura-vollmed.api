package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class WorkingHoursValidator implements AppointmentScheduleValidator {

    private static final int START_WORKING_HOUR = 7;
    private static final int END_WORKING_HOUR = 19;

    @Override
    public void validate(AppointmentScheduleData data) throws DataValidationException {
        var isSunday = data.dateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isBeforeOpening = data.dateTime().getHour() < START_WORKING_HOUR;
        var isAfterClosing = data.dateTime().getHour() > END_WORKING_HOUR;
        if (isSunday || isBeforeOpening || isAfterClosing) {
            throw new DataValidationException("Doctor is not working at this time");
        }
    }

}
