package med.voll.api.domain.doctor;

import med.voll.api.domain.common.AddressData;

public record DoctorUpdateData(
    String name,
    String phone,
    AddressData address
) {
}
