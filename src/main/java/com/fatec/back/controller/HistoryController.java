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

import com.fatec.back.domain.History.History;
import com.fatec.back.domain.History.HistoryDTO;
import com.fatec.back.service.HistoryService;


@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService service;

    @GetMapping
    public ResponseEntity<List<History>> getAll() {
        return ResponseEntity.ok(service.getAllHistories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable Long id) {
        Optional<History> dto = service.getHistoryById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<History> create(@RequestBody HistoryDTO dto) {
        System.out.println(dto);
        History saved = service.createHistory(dto);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<History> update(@PathVariable Long id, @RequestBody HistoryDTO body) {
        Optional<History> updated = service.updateHistory(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteHistory(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
