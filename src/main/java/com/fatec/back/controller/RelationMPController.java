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

import com.fatec.back.domain.RelationMP.RelationMP;
import com.fatec.back.domain.RelationMP.RelationMPDTO;
import com.fatec.back.service.RelationMPService;


@RestController
@RequestMapping("/relation_mp")
public class RelationMPController {
    @Autowired
    private RelationMPService service;

    @GetMapping
    public ResponseEntity<List<RelationMP>> getAll() {
        return ResponseEntity.ok(service.getAllRelationMPs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelationMP> getById(@PathVariable Long id) {
        Optional<RelationMP> dto = service.getRelationMPById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RelationMP> create(@RequestBody RelationMPDTO dto) {
        System.out.println(dto);
        RelationMP saved = service.createRelationMP(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RelationMP> update(@PathVariable Long id, @RequestBody RelationMPDTO body) {
        Optional<RelationMP> updated = service.updateRelationMP(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteRelationMP(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
