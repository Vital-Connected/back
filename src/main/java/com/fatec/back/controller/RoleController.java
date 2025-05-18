package com.fatec.back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.Role.RoleDTO;
import com.fatec.back.service.RoleService;

/**
 * Controlador REST responsável por gerenciar os papéis (Roles) do sistema.
 *
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todos os papéis</li>
 *   <li>Obter um papel específico por ID</li>
 *   <li>Criar um novo papel</li>
 *   <li>Atualizar um papel existente</li>
 *   <li>Excluir logicamente um papel</li>
 * </ul>
 *
 * @author Você
 * @see Role
 * @see RoleDTO
 * @see RoleService
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    /**
     * Retorna todos os papéis do sistema.
     *
     * @return Lista de {@link Role} com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    /**
     * Retorna um papel específico pelo ID.
     *
     * @param id Identificador do papel.
     * @return Objeto {@link Role} com status 200 OK, ou 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        Optional<Role> medication = service.getRoleById(id);
        return medication.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo papel no sistema.
     *
     * @param role Objeto {@link RoleDTO} com os dados do novo papel.
     * @return Papel criado com status 200 OK.
     */
    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleDTO role) {
        Role saved = service.createRole(role);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza os dados de um papel existente.
     *
     * @param id   Identificador do papel.
     * @param body Objeto {@link RoleDTO} com os novos dados.
     * @return Papel atualizado com status 200 OK, ou 404 Not Found se não for encontrado.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody RoleDTO body) {
        Optional<Role> updated = service.updateRole(id, body);
        return updated.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica (soft delete) de um papel.
     *
     * @param id Identificador do papel.
     * @return Status 204 No Content se excluído com sucesso, ou 404 Not Found se o papel não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        boolean deleted = service.deleteRole(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}