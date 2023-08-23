package project.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.domain.model.HealthCenter;

public interface HealthCenterRepository extends JpaRepository<HealthCenter, Long> {
}
