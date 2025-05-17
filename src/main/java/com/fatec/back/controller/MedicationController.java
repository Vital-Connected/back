package com.fatec.back.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Medication.MedicationDTO;
import com.fatec.back.service.MedicationService;



@RestController
@RequestMapping("/medication")
public class MedicationController {
    @Autowired
    private MedicationService service;

    @GetMapping
    public ResponseEntity<List<Medication>> getAll() {
        return ResponseEntity.ok(service.getAllMedications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getById(@PathVariable Long id) {
        Optional<Medication> dto = service.getMedicationById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medication> create(@RequestBody MedicationDTO dto) {
        System.out.println(dto);
        Medication saved = service.createMedication(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Medication> update(@PathVariable Long id, @RequestBody MedicationDTO body) {
        Optional<Medication> updated = service.updateMedication(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteMedication(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
