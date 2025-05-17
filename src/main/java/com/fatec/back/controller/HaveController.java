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

import com.fatec.back.domain.Have.Have;
import com.fatec.back.domain.Have.HaveDTO;
import com.fatec.back.service.HaveService;


@RestController
@RequestMapping("/have")
public class HaveController {
    @Autowired
    private HaveService service;

    @GetMapping
    public ResponseEntity<List<Have>> getAll() {
        return ResponseEntity.ok(service.getAllHaves());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Have> getById(@PathVariable Long id) {
        Optional<Have> dto = service.getHaveById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Have> create(@RequestBody HaveDTO dto) {
        System.out.println(dto);
        Have saved = service.createHave(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Have> update(@PathVariable Long id, @RequestBody HaveDTO body) {
        Optional<Have> updated = service.updateHave(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteHave(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
