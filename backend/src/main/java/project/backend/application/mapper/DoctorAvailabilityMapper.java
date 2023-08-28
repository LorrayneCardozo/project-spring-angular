package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.DoctorAvailabilityDTO;
import project.backend.domain.model.DoctorAvailability;

@Mapper
public interface DoctorAvailabilityMapper {
    DoctorAvailabilityMapper INSTANCE = Mappers.getMapper(DoctorAvailabilityMapper.class);

    DoctorAvailabilityDTO toDTO(DoctorAvailability doctorAvailability);

    DoctorAvailability toEntity(DoctorAvailabilityDTO doctorAvailabilityDTO);
}
