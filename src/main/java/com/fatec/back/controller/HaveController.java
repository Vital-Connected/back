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

/**
 * Controlador REST responsável pelas operações da entidade {@link Have}.
 * 
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todos os itens da tabela Have</li>
 *   <li>Buscar 'Have' por ID</li>
 *   <li>Criar um novo relacionamento Have</li>
 *   <li>Atualizar um item Have existente</li>
 *   <li>Realizar exclusão lógica (soft delete)</li>
 * </ul>
 * 
 * @see Have
 * @see HaveDTO
 * @see HaveService
 */
@RestController
@RequestMapping("/have")
public class HaveController {
    @Autowired
    private HaveService service;

     /**
     * Retorna todos os registros do tipo {@link Have}.
     *
     * @return Lista de objetos {@link Have} com status HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Have>> getAll() {
        return ResponseEntity.ok(service.getAllHaves());
    }

    /**
     * Retorna um registro do tipo {@link Have} com base no ID informado.
     *
     * @param id ID do registro a ser buscado.
     * @return Objeto {@link Have} com status 200 OK se encontrado, ou 404 Not Found caso não exista.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Have> getById(@PathVariable Long id) {
        Optional<Have> dto = service.getHaveById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo registro do tipo {@link Have} com base nos dados fornecidos.
     *
     * @param dto Objeto {@link HaveDTO} com os dados para criação.
     * @return Objeto {@link Have} criado com status HTTP 200 OK.
     *
     * @see HaveDTO
     */
    @PostMapping
    public ResponseEntity<Have> create(@RequestBody HaveDTO dto) {
        System.out.println(dto);
        Have saved = service.createHave(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza um registro existente do tipo {@link Have}.
     *
     * @param id   ID do registro a ser atualizado.
     * @param body Objeto {@link HaveDTO} com os novos dados.
     * @return Objeto {@link Have} atualizado com status 200 OK, ou 404 Not Found se o registro não for encontrado.
     *
     * @see HaveDTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Have> update(@PathVariable Long id, @RequestBody HaveDTO body) {
        Optional<Have> updated = service.updateHave(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

     /**
     * Realiza a exclusão lógica (soft delete) de um registro do tipo {@link Have}.
     * Requer o ID do usuário que está realizando a exclusão no corpo da requisição.
     *
     * @param id   ID do registro a ser excluído.
     * @param body Mapa contendo o campo {@code userId} com o ID do usuário responsável pela exclusão.
     * @return Status HTTP 204 No Content se a exclusão for bem-sucedida, ou 404 Not Found se o registro não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteHave(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
