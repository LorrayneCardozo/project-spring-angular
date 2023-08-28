package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.SchedulingDTO;
import project.backend.domain.model.Scheduling;

@Mapper
public interface SchedulingMapper {
    SchedulingMapper INSTANCE = Mappers.getMapper(SchedulingMapper.class);

    SchedulingDTO toDTO(Scheduling scheduling);
    Scheduling toModel(SchedulingDTO schedulingDTO);
}
