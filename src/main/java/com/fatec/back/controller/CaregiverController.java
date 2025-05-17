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

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Caregiver.CaregiverDTO;
import com.fatec.back.service.CaregiverService;


@RestController
@RequestMapping("/caregiver")
public class CaregiverController {
    @Autowired
    private CaregiverService service;

    @GetMapping
    public ResponseEntity<List<Caregiver>> getAll() {
        return ResponseEntity.ok(service.getAllCaregivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caregiver> getById(@PathVariable Long id) {
        Optional<Caregiver> dto = service.getCaregiverById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Caregiver> create(@RequestBody CaregiverDTO dto) {
        System.out.println(dto);
        Caregiver saved = service.createCaregiver(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Caregiver> update(@PathVariable Long id, @RequestBody CaregiverDTO body) {
        Optional<Caregiver> updated = service.updateCaregiver(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteCaregiver(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
