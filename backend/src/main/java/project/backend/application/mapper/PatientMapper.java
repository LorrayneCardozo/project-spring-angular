package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.DoctorDTO;
import project.backend.application.dto.PatientDTO;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Patient;

@Mapper
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO toDTO(Patient patient);

    Patient toEntity(PatientDTO patientDTO);
}
