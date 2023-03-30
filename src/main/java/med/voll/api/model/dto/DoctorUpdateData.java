package med.voll.api.model.dto;

public record DoctorUpdateData(
    String name,
    String phone,
    AddressData address
) {
}
