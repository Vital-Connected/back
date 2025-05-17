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

import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.Patient.PatientDTO;
import com.fatec.back.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService service;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {
        return ResponseEntity.ok(service.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        Optional<Patient> dto = service.getPatientById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody PatientDTO dto) {
        System.out.println(dto);
        Patient saved = service.createPatient(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody PatientDTO body) {
        Optional<Patient> updated = service.updatePatient(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deletePatient(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
