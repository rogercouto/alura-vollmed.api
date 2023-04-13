package med.voll.api.domain.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.common.Address;

@Table(name = "patient")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    @Embedded
    private Address address;
    private boolean active;

    public Patient(PatientCreateData data) {
        name = data.name();
        email = data.email();
        phone = data.phone();
        cpf = data.cpf();
        address = new Address(data.address());
        active = true;
    }

    public void updateFields(PatientUpdateData data) {
        if (data.name() != null) {
            name = data.name();
        }
        if (data.phone() != null) {
            phone = data.phone();
        }
        if (data.address() != null) {
            address.updateFields(data.address());
        }
    }

    public void deactivate() {
        active = false;
    }

}
