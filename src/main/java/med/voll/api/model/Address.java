package med.voll.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.dto.AddressData;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String neighborhood;
    @Column(name = "postal_code")
    private String postalCode;
    private String city;
    private String state;
    private String complement;
    private String number;

    public Address(AddressData request) {
        street = request.street();
        neighborhood = request.neighborhood();
        postalCode = request.postalCode();
        city = request.city();
        state = request.state();
        complement = request.complement();
        number = request.number();
    }

    public boolean updateFields(AddressData request) {
        boolean wasUpdated = false;
        if (request.street() != null && !request.street().isBlank()) {
            street = request.street();
            wasUpdated = true;
        }
        if (request.neighborhood() != null && !request.neighborhood().isBlank()) {
            neighborhood = request.neighborhood();
            wasUpdated = true;
        }
        if (request.postalCode() != null && !request.postalCode().isBlank()) {
            postalCode = request.postalCode();
            wasUpdated = true;
        }
        if (request.city() != null && !request.city().isBlank()) {
            city = request.city();
            wasUpdated = true;
        }
        if (request.state() != null && !request.state().isBlank()) {
            state = request.state();
            wasUpdated = true;
        }
        if (request.complement() != null && !request.complement().isBlank()) {
            complement = request.complement();
            wasUpdated = true;
        }
        if (request.number() != null && !request.number().isBlank()) {
            number = request.number();
            wasUpdated = true;
        }
        return wasUpdated;
    }

}
