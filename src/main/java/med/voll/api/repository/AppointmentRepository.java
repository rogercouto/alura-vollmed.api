package med.voll.api.repository;

import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor = :doctor and a.dateTime = :dateTime")
    Appointment findByDoctorAndDateTime(Doctor doctor, LocalDateTime dateTime);

}
