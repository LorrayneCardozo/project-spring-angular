package project.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Person;
import project.backend.domain.model.Scheduling;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
    @Query("SELECT s FROM Scheduling s WHERE s.doctor = :doctor AND s.appointmentTime = :appointmentTime AND s.appointmentDate = :appointmentDate")
    List<Scheduling> findByDoctorAndAppointmentTime(Doctor doctor, LocalTime appointmentTime, LocalDate appointmentDate);

    List<Scheduling> findByPerson(Person person);
    List<Scheduling> findByDoctor(Doctor doctor);

    @Query("SELECT s FROM Scheduling s WHERE (s.person = :person OR s.doctor = :doctor) AND s.appointmentDate = :date")
    List<Scheduling> findByPersonOrDoctorAndDate(
            @Param("person") Person person,
            @Param("doctor") Doctor doctor,
            @Param("date") LocalDate date
    );
}
