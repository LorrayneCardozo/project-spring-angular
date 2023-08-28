package project.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.application.mapper.HealthCenterMapper;
import project.backend.domain.model.HealthCenter;
import project.backend.domain.repository.HealthCenterRepository;
import project.backend.application.dto.HealthCenterDTO;
import javax.validation.Validator;
import java.util.List;


@Service
public class HealthCenterService {
    private final HealthCenterRepository healthCenterRepository;
    private final HealthCenterMapper healthCenterMapper = HealthCenterMapper.INSTANCE;
    private final Validator validator;

    @Autowired
    public HealthCenterService(HealthCenterRepository healthCenterRepository, Validator validator) {
        this.healthCenterRepository = healthCenterRepository;
        this.validator = validator;
    }

    public List<HealthCenter> getAllHealthCenters() {
        return healthCenterRepository.findAll();
    }

    public HealthCenter getHealthCenterById(Long id) {
        return healthCenterRepository.findById(id).orElse(null);
    }

    public HealthCenterDTO createHealthCenter(HealthCenterDTO healthCenterDTO) {
        HealthCenter healthCenter = healthCenterMapper.toModel(healthCenterDTO);
        return healthCenterMapper.toDTO(healthCenterRepository.save(healthCenter));
    }

    public HealthCenter updateHealthCenter(Long id, HealthCenter updatedHealthCenter) {
        HealthCenter existingHealthCenter = healthCenterRepository.findById(id).orElse(null);

        if (existingHealthCenter != null) {
            BeanUtils.copyProperties(updatedHealthCenter, existingHealthCenter, "id");
            return healthCenterRepository.save(existingHealthCenter);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteHealthCenter(Long id) {
        if (healthCenterRepository.existsById(id)) {
            healthCenterRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
