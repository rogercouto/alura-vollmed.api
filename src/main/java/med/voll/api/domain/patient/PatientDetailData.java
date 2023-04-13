package med.voll.api.domain.patient;

import med.voll.api.domain.common.AddressData;

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
