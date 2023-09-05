package project.backend.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.backend.application.dto.DoctorAvailabilityDTO;
import project.backend.application.mapper.DoctorAvailabilityMapper;
import project.backend.domain.model.DoctorAvailability;
import project.backend.domain.model.Doctor;
import project.backend.domain.repository.DoctorAvailabilityRepository;
import project.backend.domain.repository.DoctorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper = DoctorAvailabilityMapper.INSTANCE;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorAvailabilityService(DoctorAvailabilityRepository doctorAvailabilityRepository, DoctorRepository doctorRepository) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorAvailabilityDTO> findAll(){
        return doctorAvailabilityRepository.findAll()
                .stream()
                .map(doctorAvailabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorAvailabilityDTO> getDoctorAvailabilities(Long doctorId) {
        return doctorAvailabilityRepository.findByDoctor_Id(doctorId)
                .stream()
                .map(doctorAvailabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorAvailability createDoctorAvailability(Long doctorId, DoctorAvailabilityDTO request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        DoctorAvailability availability = new DoctorAvailability();
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());

        availability.setDoctor(doctor);
        doctor.getAvailabilities().add(availability);

        doctorRepository.save(doctor);

        return availability;
    }
    public DoctorAvailabilityDTO findById(Long id) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DoctorAvailability not found"));

        return doctorAvailabilityMapper.toDTO(doctorAvailability);
    }

    public DoctorAvailabilityDTO updateDoctorAvailability(Long id, DoctorAvailabilityDTO request) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DoctorAvailability not found"));

        BeanUtils.copyProperties(request, doctorAvailability, "id");
        doctorAvailabilityRepository.save(doctorAvailability);
        return doctorAvailabilityMapper.toDTO(doctorAvailability);
    }

    public void deleteDoctorAvailability(Long id) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DoctorAvailability not found"));

        doctorAvailabilityRepository.delete(doctorAvailability);
    }
}
