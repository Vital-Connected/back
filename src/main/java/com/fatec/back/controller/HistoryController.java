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

/**
 * Controlador REST responsável pelas operações da entidade {@link History}.
 * 
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todos os históricos</li>
 *   <li>Buscar histórico por ID</li>
 *   <li>Criar um novo histórico</li>
 *   <li>Atualizar um histórico existente</li>
 *   <li>Realizar exclusão lógica (soft delete)</li>
 * </ul>
 * 
 * @author Você
 * @see History
 * @see HistoryDTO
 * @see HistoryService
 */
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService service;

    /**
     * Retorna todos os registros da entidade {@link History}.
     *
     * @return Lista de históricos com status HTTP 200 OK.
    */
    @GetMapping
    public ResponseEntity<List<History>> getAll() {
        return ResponseEntity.ok(service.getAllHistories());
    }

     /**
     * Busca um histórico pelo ID informado.
     *
     * @param id Identificador do histórico.
     * @return Histórico correspondente com status 200 OK, ou 404 Not Found se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable Long id) {
        Optional<History> dto = service.getHistoryById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo histórico com base nos dados recebidos.
     *
     * @param dto Objeto {@link HistoryDTO} com os dados do novo histórico.
     * @return Histórico criado com status 200 OK.
     */
    @PostMapping
    public ResponseEntity<History> create(@RequestBody HistoryDTO dto) {
        System.out.println(dto);
        History saved = service.createHistory(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza os dados de um histórico existente.
     *
     * @param id   Identificador do histórico a ser atualizado.
     * @param body Objeto {@link HistoryDTO} com os novos dados.
     * @return Histórico atualizado com status 200 OK, ou 404 Not Found se não existir.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<History> update(@PathVariable Long id, @RequestBody HistoryDTO body) {
        Optional<History> updated = service.updateHistory(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica de um histórico (soft delete).
     *
     * @param id   Identificador do histórico a ser excluído.
     * @param body Mapa contendo o campo {@code userId}, com o ID do usuário que executa a exclusão.
     * @return Status 204 No Content se a exclusão for bem-sucedida, ou 404 Not Found caso contrário.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteHistory(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
