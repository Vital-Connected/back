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

/**
 * Controlador REST responsável por gerenciar as relações entre {@code Medication} e {@code Patient} (RelationMP).
 *
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todas as relações</li>
 *   <li>Obter uma relação específica por ID</li>
 *   <li>Criar uma nova relação</li>
 *   <li>Atualizar uma relação existente</li>
 *   <li>Realizar exclusão lógica (soft delete) de uma relação</li>
 * </ul>
 *
 * @author Você
 * @see RelationMP
 * @see RelationMPDTO
 * @see RelationMPService
 */
@RestController
@RequestMapping("/relation_mp")
public class RelationMPController {
    @Autowired
    private RelationMPService service;

    /**
     * Retorna todas as relações entre medicamentos e pacientes.
     *
     * @return Lista de {@link RelationMP} com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<RelationMP>> getAll() {
        return ResponseEntity.ok(service.getAllRelationMPs());
    }

    /**
     * Retorna uma relação específica pelo ID.
     *
     * @param id Identificador da relação.
     * @return Objeto {@link RelationMP} com status 200 OK, ou 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RelationMP> getById(@PathVariable Long id) {
        Optional<RelationMP> dto = service.getRelationMPById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova relação entre medicamento e paciente.
     *
     * @param dto Objeto {@link RelationMPDTO} com os dados da relação.
     * @return Relação criada com status 200 OK.
     */
    @PostMapping
    public ResponseEntity<RelationMP> create(@RequestBody RelationMPDTO dto) {
        System.out.println(dto);
        RelationMP saved = service.createRelationMP(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza uma relação existente entre medicamento e paciente.
     *
     * @param id   Identificador da relação.
     * @param body Objeto {@link RelationMPDTO} com os novos dados.
     * @return Relação atualizada com status 200 OK, ou 404 Not Found se não for encontrada.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<RelationMP> update(@PathVariable Long id, @RequestBody RelationMPDTO body) {
        Optional<RelationMP> updated = service.updateRelationMP(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica (soft delete) de uma relação entre medicamento e paciente.
     *
     * @param id   Identificador da relação.
     * @param body Mapa contendo o campo {@code userId}, representando o usuário que realizou a exclusão.
     * @return Status 204 No Content se excluído com sucesso, ou 404 Not Found se a relação não for encontrada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteRelationMP(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}