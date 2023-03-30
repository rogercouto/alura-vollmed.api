package med.voll.api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorCreateData(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String phone,
    @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,
    @NotNull SpecialtyData specialty,
    @NotNull AddressData address
) {
}
