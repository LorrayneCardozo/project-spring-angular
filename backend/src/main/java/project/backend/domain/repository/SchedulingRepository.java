package project.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Scheduling;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
    List<Scheduling> findByDoctorAndAppointmentTime(Doctor doctor, LocalTime appointmentTime);
}
