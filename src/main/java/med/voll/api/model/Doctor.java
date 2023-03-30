package med.voll.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import med.voll.api.model.dto.DoctorCreateData;
import med.voll.api.model.dto.DoctorUpdateData;

@Table(name = "doctor")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("nome")
    private String name;
    private String email;
    private String phone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    private boolean active;

    public Doctor(DoctorCreateData body) {
        name = body.name();
        email = body.email();
        phone = body.phone();
        crm = body.crm();
        specialty = Specialty.fromRequest(body.specialty());
        address = new Address(
            body.address().street(),
            body.address().neighborhood(),
            body.address().postalCode(),
            body.address().city(),
            body.address().state(),
            body.address().complement(),
            body.address().number()
        );
        active = true;
    }

    public boolean updateFields(DoctorUpdateData body) {
        boolean wasUpdated = false;
        if (body.name() != null && !body.name().isBlank()) {
            name = body.name();
            wasUpdated = true;
        }
        if (body.phone() != null && !body.phone().isBlank()) {
            phone = body.phone();
            wasUpdated = true;
        }
        if (body.address() != null) {
            wasUpdated = address.updateFields(body.address());
        }
        return wasUpdated;
    }

    public void deactivate() {
        active = false;
    }

}
