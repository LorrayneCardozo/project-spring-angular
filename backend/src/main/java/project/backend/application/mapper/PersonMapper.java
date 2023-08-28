package project.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.backend.application.dto.PersonDTO;
import project.backend.domain.model.Person;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO toDTO(Person person);

    Person toEntity(PersonDTO personDTO);
}
