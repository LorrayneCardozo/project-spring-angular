package project.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.application.dto.SchedulingDTO;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Person;
import project.backend.service.SchedulingService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/scheduling")
@Api(tags = "Scheduling", description = "API para operações relacionadas aos agendamentos de consultas")
public class SchedulingController {
    SchedulingService schedulingService;

    @Autowired
    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os agendamentos cadastrados")
    public List<SchedulingDTO> findAll(){
        return schedulingService.findAll();
    }

    @GetMapping("/person/{person}")
    @ApiOperation(value = "Listar todos os agendamentos de um paciente")
    public List<SchedulingDTO> findByPerson(Person person){
        return schedulingService.findByPerson(person);
    }

    @GetMapping("/doctor/{doctor}")
    @ApiOperation(value = "Listar todos os agendamentos de um médico")
    public List<SchedulingDTO> findByDoctor(Doctor doctor){
        return schedulingService.findByDoctor(doctor);
    }

    @GetMapping("/person-doctor-date")
    @ApiOperation(value = "Lista agendamentos de um paciente ou médico em um determinado dia")
    public List<SchedulingDTO> findByPersonOrDoctorAndDate(
            @RequestParam(name = "idPerson", required = false) Person person,
            @RequestParam(name = "idDoctor", required = false) Doctor doctor,
            @RequestParam(name = "date") String dateString
    ) {
        LocalDate date = LocalDate.parse(dateString);
        return schedulingService.findByPersonOrDoctorAndDate(person, doctor, date);
    }

    @PostMapping
    @ApiOperation(value = "Criar um novo agendamento")
    public ResponseEntity<SchedulingDTO> createScheduling(@RequestBody SchedulingDTO schedulingDTO) {
        SchedulingDTO createdSchedulingDTO = schedulingService.createScheduling(schedulingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedulingDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Listar um agendamento pelo ID")
    public ResponseEntity<SchedulingDTO> findById(@PathVariable Long id) {
        SchedulingDTO schedulingDTO = schedulingService.findById(id);
        return ResponseEntity.ok(schedulingDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar um agendamento pelo ID")
    public ResponseEntity<SchedulingDTO> updateScheduling(@PathVariable Long id, @RequestBody SchedulingDTO updatedSchedulingDTO) {
        SchedulingDTO updatedScheduling = schedulingService.updateScheduling(id, updatedSchedulingDTO);
        return ResponseEntity.ok(updatedScheduling);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Apagar um agendamento pelo ID")
    public ResponseEntity<Void> deleteScheduling(@PathVariable Long id) {
        schedulingService.deleteScheduling(id);
        return ResponseEntity.noContent().build();
    }
}
