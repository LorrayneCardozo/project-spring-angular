package project.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.application.dto.DoctorAvailabilityDTO;
import project.backend.domain.model.DoctorAvailability;
import project.backend.service.DoctorAvailabilityService;
import java.time.Duration;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/doctorAvailability")
@Api(tags = "Doctor Availability", description = "API para operações relacionadas aos horários disponíveis de um médico")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    @Autowired
    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @GetMapping
    public List<DoctorAvailabilityDTO> findAll() {
        return doctorAvailabilityService.findAll();
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar horário disponível de um médico")
    public ResponseEntity<DoctorAvailability> createAvailability(@RequestBody DoctorAvailabilityDTO request) {
        DoctorAvailability createdAvailability = doctorAvailabilityService.createDoctorAvailability(request.getDoctor().getId(), request);
        return ResponseEntity.ok(createdAvailability);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar horário disponível por ID")
    public DoctorAvailabilityDTO getAvailabilityById(@PathVariable Long id) {
        return doctorAvailabilityService.findById(id);
    }

    @GetMapping("/doctor/{id_doctor}")
    @ApiOperation(value = "Buscar horário disponível por médico")
    public List<DoctorAvailabilityDTO> getDoctorAvailability(@PathVariable Long id_doctor) {
        return doctorAvailabilityService.getDoctorAvailabilities(id_doctor);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar horário disponível por ID")
    public ResponseEntity<DoctorAvailabilityDTO> updateAvailability(@PathVariable Long id, @RequestBody DoctorAvailabilityDTO request) {
        DoctorAvailabilityDTO updatedAvailability = doctorAvailabilityService.updateDoctorAvailability(id, request);
        return ResponseEntity.ok(updatedAvailability);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir horário disponível por ID")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        doctorAvailabilityService.deleteDoctorAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
