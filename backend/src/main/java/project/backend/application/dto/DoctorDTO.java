package project.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.jetbrains.annotations.NotNull;
import project.backend.domain.enums.SpecialtyType;
import project.backend.domain.model.HealthCenter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    @CPF
    private String cpf;
    private String name;
    @Enumerated(EnumType.STRING)
    private SpecialtyType specialty;
    private String phone;
    private HealthCenter healthCenter;
    private List<DoctorAvailabilityDTO> availabilities;
}
