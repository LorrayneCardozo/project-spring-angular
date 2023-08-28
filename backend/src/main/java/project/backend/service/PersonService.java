package project.backend.service;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.backend.domain.model.Person;
import project.backend.domain.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Data
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    public Person createPerson(String cpf, String name, String phone, String username, String password, String role) {
        Person person = new Person();
        person.setCpf(cpf);
        person.setName(name);
        person.setPhone(phone);
        person.setUsername(username);
        person.setPassword(passwordEncoder.encode(password));
        person.setRole(role);
        return personRepository.save(person);
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = personRepository.findById(id).orElse(null);

        if (existingPerson != null) {
            existingPerson.setName(updatedPerson.getName());
            existingPerson.setPhone(updatedPerson.getPhone());
            existingPerson.setUsername(updatedPerson.getUsername());
            existingPerson.setRole(updatedPerson.getRole());

            if (!updatedPerson.getPassword().equals(existingPerson.getPassword())) {
                existingPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
            }

            return personRepository.save(existingPerson);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deletePerson(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            personRepository.delete(person);
            return true;
        }
        return false;
    }
}
