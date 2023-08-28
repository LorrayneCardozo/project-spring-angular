package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.DoctorDTO;
import project.backend.domain.model.Doctor;

@Mapper
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    Doctor toModel(DoctorDTO doctorDTO);
    DoctorDTO toDTO(Doctor doctor);
}
