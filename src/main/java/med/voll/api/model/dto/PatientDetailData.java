package med.voll.api.model.dto;

import med.voll.api.model.Address;
import med.voll.api.model.Patient;

public record PatientDetailData(
    Long id,
    String name,
    String email,
    String cpf,
    String phone,
    AddressData address
) {

    public PatientDetailData(Patient patient) {
        this(
            patient.getId(),
            patient.getName(),
            patient.getEmail(),
            patient.getCpf(),
            patient.getPhone(),
            new AddressData(patient.getAddress())
        );
    }

}
