package med.voll.api.domain.patient;

import med.voll.api.domain.common.AddressData;

public record PatientUpdateData(
    String name,
    String phone,
    AddressData address
) {
}
