package med.voll.api.model.dto;

public record PatientUpdateData(
    String name,
    String phone,
    AddressData address
) {
}
