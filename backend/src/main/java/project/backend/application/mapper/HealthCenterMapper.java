package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.HealthCenterDTO;
import project.backend.domain.model.HealthCenter;

@Mapper
public interface HealthCenterMapper {
    HealthCenterMapper INSTANCE = Mappers.getMapper(HealthCenterMapper.class);

    HealthCenterDTO toDTO(HealthCenter healthCenter);

    HealthCenter toEntity(HealthCenterDTO healthCenterDTO);
}
