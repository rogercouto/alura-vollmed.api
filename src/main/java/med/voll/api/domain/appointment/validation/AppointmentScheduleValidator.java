package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;

public interface AppointmentScheduleValidator {

    void validate(AppointmentScheduleData data) throws DataValidationException;

}
