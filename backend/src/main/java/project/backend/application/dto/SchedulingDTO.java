package project.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.backend.domain.enums.SchedulingStatus;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Person;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingDTO {
    private Long id;
    private LocalTime appointmentTime;
    private LocalDate appointmentDate;
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;
    private Doctor doctor;
    private Person person;
}
