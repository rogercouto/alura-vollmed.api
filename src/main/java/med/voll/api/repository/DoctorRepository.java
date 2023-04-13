package med.voll.api.repository;

import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByIdAndActiveTrue(Long id);

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query("""
       select d from Doctor d
       where d.active = true
       and
       d.specialty = :specialty
       and
       d.id not in(
           select a.doctor.id from Appointment a
           where
           a.dateTime = :dateTime
       )
       order by rand()
       limit 1
    """)
    Doctor chooseRandomDoctorOnDate(Specialty specialty, LocalDateTime dateTime);

}
