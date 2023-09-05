package project.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.backend.application.dto.DoctorAvailabilityDTO;
import project.backend.application.dto.DoctorDTO;
import project.backend.application.mapper.DoctorMapper;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.DoctorAvailability;
import project.backend.domain.repository.DoctorRepository;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper = DoctorMapper.INSTANCE;
    private final Validator validator;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, Validator validator) {
        this.doctorRepository = doctorRepository;
        this.validator = validator;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toModel(doctorDTO);

        List<DoctorAvailability> availabilities = new ArrayList<>();
        for (DoctorAvailabilityDTO availabilityDTO : doctorDTO.getAvailabilities()) {
            DoctorAvailability availability = new DoctorAvailability();
            availability.setDoctor(doctor);
            availability.setDayOfWeek(availabilityDTO.getDayOfWeek());
            availability.setStartTime(availabilityDTO.getStartTime());
            availability.setEndTime(availabilityDTO.getEndTime());
            availabilities.add(availability);
        }
        doctor.setAvailabilities(availabilities);
        return doctorMapper.toDTO(doctorRepository.save(doctor));
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);

        if (existingDoctor != null) {
            BeanUtils.copyProperties(updatedDoctor, existingDoctor, "id");
            return doctorRepository.save(existingDoctor);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
