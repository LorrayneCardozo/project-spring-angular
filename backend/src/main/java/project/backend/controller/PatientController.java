package project.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.domain.model.Patient;
import project.backend.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patient")
@Api(tags = "Patient", description = "API para operações relacionadas aos pacientes")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation(value = "Listar todos os Pacientes cadastrados")
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @ApiOperation(value = "Buscar Paciente pelo CPF")
    @GetMapping("/{cpf}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String cpf) {
        Patient patient = patientService.getPatientByCpf(cpf);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Criar novo Paciente")
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @ApiOperation(value = "Atualizar Paciente pelo CPF")
    @PutMapping("/{cpf}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String cpf, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(cpf, patient);
        if (updatedPatient != null) {
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Deletar Paciente pelo CPF")
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletePatient(@PathVariable String cpf) {
        patientService.deletePatient(cpf);
        return ResponseEntity.noContent().build();
    }
}
