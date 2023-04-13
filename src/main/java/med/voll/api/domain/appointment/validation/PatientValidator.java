package med.voll.api.domain.appointment.validation;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.DataValidationException;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientValidator implements AppointmentScheduleValidator{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void validate(AppointmentScheduleData data) throws DataValidationException {
        var exists = patientRepository.existsByIdAndActiveTrue(data.patientId());
        if (!exists) {
            throw new DataValidationException("Patient not exists or is inactive");
        }
    }

}
