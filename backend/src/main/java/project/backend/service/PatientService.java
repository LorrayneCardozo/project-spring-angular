package project.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.domain.model.Patient;
import project.backend.domain.repository.PatientRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final Validator validator;

    @Autowired
    public PatientService(PatientRepository patientRepository, Validator validator) {
        this.patientRepository = patientRepository;
        this.validator = validator;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientByCpf(String cpf) {
        return patientRepository.findByCpf(cpf);
    }

    public Patient createPatient(Patient patient) {
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(String cpf, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findByCpf(cpf);

        if (existingPatient != null) {
            BeanUtils.copyProperties(updatedPatient, existingPatient, "cpf");
            return patientRepository.save(existingPatient);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deletePatient(String cpf) {
        if (patientRepository.existsByCpf(cpf)) {
            patientRepository.deleteByCpf(cpf);
            return true;
        }
        return false;
    }
}
