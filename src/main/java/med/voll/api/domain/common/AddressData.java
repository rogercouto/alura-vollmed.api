package med.voll.api.domain.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(
    @NotBlank String street,
    @NotBlank String neighborhood,
    @NotBlank @Pattern(regexp = "\\d{8}") String postalCode,
    @NotBlank String city,
    @NotBlank String state,
    String complement,
    String number
){
    public AddressData(Address address) {
        this(
            address.getStreet(),
            address.getNeighborhood(),
            address.getPostalCode(),
            address.getCity(),
            address.getState(),
            address.getComplement(),
            address.getNumber()
        );
    }

}
