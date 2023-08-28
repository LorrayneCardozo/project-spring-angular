package project.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.domain.model.Person;
import project.backend.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@Api(tags = "Person", description = "API para operações relacionadas aos usuários")
@Data
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //@PreAuthorize("hasRole('DOCTOR') OR hasRole('ADMIN')")
    @PostMapping()
    @ApiOperation(value = "Cadastra uma Pessoa no sistema")
    public ResponseEntity<String> registerPerson(@RequestBody RegisterRequest registerRequest) {
        personService.createPerson(
                registerRequest.getCpf(),
                registerRequest.getName(),
                registerRequest.getPhone(),
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getRole()
        );
        return new ResponseEntity<>("Person registered successfully.", HttpStatus.CREATED);
    }
    @Data
    static class RegisterRequest {
        private String cpf;
        private String name;
        private String phone;
        private String username;
        private String password;
        private String role;
    }

    @ApiOperation(value = "Listar todas as Pessoas cadastradas")
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @ApiOperation(value = "Buscar uma Pessoa pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Atualizar Pessoa pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Deletar Pessoa pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean deleted = personService.deletePerson(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

