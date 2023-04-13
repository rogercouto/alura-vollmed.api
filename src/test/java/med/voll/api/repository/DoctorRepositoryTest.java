package med.voll.api.repository;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.common.Address;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager entityManager;

    private static final Doctor DOCTOR_1 = new Doctor(
        null,
        "Dr House",
        "drhouse@gmail.com",
        "5532612345678",
        "123456",
        Specialty.CARDIOLOGY,
        new Address("Rua 1",
                "Bairro 1",
                "12345-678",
                "Cidade 1",
                "RS",
                "Complemento 1",
                "1"),
        true
    );

    private static final Doctor DOCTOR_2 = new Doctor(
        null,
        "Meredith Grey",
        "meredithgrey@gmail.com",
        "5532612345679",
        "234567",
        Specialty.GYNECOLOGY,
        new Address("Rua 3",
                "Bairro 3",
                "12345-678",
                "Cidade 3",
                "RS",
                "Complemento 3",
                "1"),
        true
    );

    @Test
    @DisplayName("Should return a random doctor with the given specialty")
    void chooseRandomCardiology() {
        registerDoctor(DOCTOR_1);
        registerDoctor(DOCTOR_2);

        var nextWeekAt10 = LocalDateTime.now().plusWeeks(1).withHour(10).withMinute(0).withSecond(0);
        var freeDoctor = doctorRepository.chooseRandomDoctorOnDate(Specialty.CARDIOLOGY, nextWeekAt10);

        Assertions.assertThat(freeDoctor).isNotNull();
        Assertions.assertThat(freeDoctor.getSpecialty()).isEqualTo(Specialty.CARDIOLOGY);
        Assertions.assertThat(freeDoctor.getName().equals(DOCTOR_1.getName())).isTrue();
    }

    @Test
    @DisplayName("Should return a random doctor with the given specialty")
    void chooseNullDoctor() {
        var nextWeekAt10 = LocalDateTime.now().plusWeeks(1).withHour(10).withMinute(0).withSecond(0);
        var freeDoctor = doctorRepository.chooseRandomDoctorOnDate(Specialty.ORTHOPEDICS, nextWeekAt10);

        Assertions.assertThat(freeDoctor).isNull();
    }

    private void registerDoctor(Doctor doctor) {
        entityManager.persist(doctor);
    }
}