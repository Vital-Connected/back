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


/**
 * Controlador REST responsável pelas operações da entidade {@link Medication}.
 *
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todos os medicamentos</li>
 *   <li>Buscar medicamento por ID</li>
 *   <li>Criar um novo medicamento</li>
 *   <li>Atualizar um medicamento existente</li>
 *   <li>Realizar exclusão lógica (soft delete)</li>
 * </ul>
 *
 * @author Você
 * @see Medication
 * @see MedicationDTO
 * @see MedicationService
 */
@RestController
@RequestMapping("/medication")
public class MedicationController {
    @Autowired
    private MedicationService service;

    /**
     * Retorna todos os registros da entidade {@link Medication}.
     *
     * @return Lista de medicamentos com status HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Medication>> getAll() {
        return ResponseEntity.ok(service.getAllMedications());
    }

    /**
     * Busca um medicamento pelo ID informado.
     *
     * @param id Identificador do medicamento.
     * @return Medicamento correspondente com status 200 OK, ou 404 Not Found se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medication> getById(@PathVariable Long id) {
        Optional<Medication> dto = service.getMedicationById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo medicamento com base nos dados recebidos.
     *
     * @param dto Objeto {@link MedicationDTO} com os dados do novo medicamento.
     * @return Medicamento criado com status 200 OK.
     */
    @PostMapping
    public ResponseEntity<Medication> create(@RequestBody MedicationDTO dto) {
        System.out.println(dto);
        Medication saved = service.createMedication(dto);
        return ResponseEntity.ok(saved);
    }

     /**
     * Atualiza os dados de um medicamento existente.
     *
     * @param id   Identificador do medicamento a ser atualizado.
     * @param body Objeto {@link MedicationDTO} com os novos dados.
     * @return Medicamento atualizado com status 200 OK, ou 404 Not Found se não existir.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Medication> update(@PathVariable Long id, @RequestBody MedicationDTO body) {
        Optional<Medication> updated = service.updateMedication(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica de um medicamento (soft delete).
     *
     * @param id   Identificador do medicamento a ser excluído.
     * @param body Mapa contendo o campo {@code userId}, com o ID do usuário que executa a exclusão.
     * @return Status 204 No Content se a exclusão for bem-sucedida, ou 404 Not Found caso contrário.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteMedication(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}