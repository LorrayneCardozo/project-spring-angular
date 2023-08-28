package project.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.backend.domain.enums.SchedulingStatus;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingDTO {
    private Long id;
    private LocalTime appointmentTime;
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;
    private Doctor doctor;
    private Person person;
}
