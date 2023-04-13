package med.voll.api.repository;


import med.voll.api.domain.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByIdAndActiveTrue(Long id);

    Page<Patient> findAllByActiveTrue(Pageable pageable);

}
