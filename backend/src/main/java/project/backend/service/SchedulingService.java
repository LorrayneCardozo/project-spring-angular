package project.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.backend.application.dto.SchedulingDTO;
import project.backend.application.mapper.SchedulingMapper;
import project.backend.domain.model.Doctor;
import project.backend.domain.model.Scheduling;
import project.backend.domain.repository.DoctorRepository;
import project.backend.domain.repository.SchedulingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SchedulingService {
    private SchedulingRepository schedulingRepository;
    private DoctorRepository doctorRepository;
    private SchedulingMapper schedulingMapper = SchedulingMapper.INSTANCE;

    @Autowired
    public SchedulingService(SchedulingRepository schedulingRepository, DoctorRepository doctorRepository) {
        this.schedulingRepository = schedulingRepository;
        this.doctorRepository = doctorRepository;
    }

    @Autowired


    public List<SchedulingDTO> findAll() {
        return schedulingRepository.findAll()
                .stream()
                .map(schedulingMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public SchedulingDTO createScheduling(SchedulingDTO schedulingDTO) {
        Scheduling scheduling = schedulingMapper.toModel(schedulingDTO);

        if (isConflict(scheduling)) {
            throw new IllegalStateException("Conflito de agendamento: o médico já tem um agendamento para esse horário.");
        }

        return schedulingMapper.toDTO(schedulingRepository.save(scheduling));
    }

    public SchedulingDTO findById(Long id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));

        return schedulingMapper.toDTO(scheduling);
    }

    @Transactional
    public SchedulingDTO updateScheduling(Long id, SchedulingDTO updatedSchedulingDTO) {
        Scheduling existingScheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));

        Scheduling updatedScheduling = schedulingMapper.toModel(updatedSchedulingDTO);
        BeanUtils.copyProperties(updatedScheduling, existingScheduling, "id");

        if (isConflict(existingScheduling)) {
            throw new IllegalStateException("Conflito de agendamento: o médico já tem um agendamento para esse horário.");
        }

        return schedulingMapper.toDTO(schedulingRepository.save(existingScheduling));
    }

    @Transactional
    public void deleteScheduling(Long id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));

        schedulingRepository.delete(scheduling);
    }

    private boolean isConflict(Scheduling newScheduling) {
        Doctor doctor = doctorRepository.findById(newScheduling.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        List<Scheduling> existingSchedulings = schedulingRepository.findByDoctorAndAppointmentTime(
                doctor,
                newScheduling.getAppointmentTime()
        );
        if(existingSchedulings.get(0).getId() == newScheduling.getId())
            return false;

        return !existingSchedulings.isEmpty();
    }

}
