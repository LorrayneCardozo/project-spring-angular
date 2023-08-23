package project.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.domain.model.HealthCenter;
import project.backend.service.HealthCenterService;

import java.util.List;

@RestController
@RequestMapping("/healthCenter")
@Api(tags = "Health Center", description = "API para operações relacionadas às unidades de saúde")
public class HealthCenterController {

    private final HealthCenterService healthCenterService;

    @Autowired
    public HealthCenterController(HealthCenterService healthCenterService) {
        this.healthCenterService = healthCenterService;
    }

    @ApiOperation(value = "Listar todas as unidades de saúde cadastradas")
    @GetMapping
    public ResponseEntity<List<HealthCenter>> getAllHealthCenters() {
        List<HealthCenter> healthCenters = healthCenterService.getAllHealthCenters();
        return ResponseEntity.ok(healthCenters);
    }

    @ApiOperation(value = "Buscar uma unidade de saúde pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<HealthCenter> getHealthCenterById(@PathVariable Long id) {
        HealthCenter healthCenter = healthCenterService.getHealthCenterById(id);
        if (healthCenter != null) {
            return ResponseEntity.ok(healthCenter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Criar uma nova unidade de saúde")
    @PostMapping
    public ResponseEntity<HealthCenter> createHealthCenter(@RequestBody HealthCenter healthCenter) {
        HealthCenter createdHealthCenter = healthCenterService.createHealthCenter(healthCenter);
        return ResponseEntity.ok(createdHealthCenter);
    }

    @ApiOperation(value = "Atualizar unidade de saúde pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<HealthCenter> updateHealthCenter(@PathVariable Long id, @RequestBody HealthCenter healthCenter) {
        HealthCenter updatedHealthCenter = healthCenterService.updateHealthCenter(id, healthCenter);
        if (updatedHealthCenter != null) {
            return ResponseEntity.ok(updatedHealthCenter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Deletar unidade de saúde pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthCenter(@PathVariable Long id) {
        healthCenterService.deleteHealthCenter(id);
        return ResponseEntity.noContent().build();
    }
}
