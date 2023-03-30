package med.voll.api.model.dto;

import med.voll.api.model.Patient;

public record PatientListData(
    Long id,
    String name,
    String email,
    String cpf
) {

    public PatientListData(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }

}
