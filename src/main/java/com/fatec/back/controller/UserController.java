package com.fatec.back.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.back.domain.User.User;
import com.fatec.back.service.UserService;

/**
 * Controlador REST responsável pelo gerenciamento dos usuários do sistema.
 *
 * Fornece endpoints para:
 * <ul>
 *   <li>Listar todos os usuários</li>
 *   <li>Obter um usuário por ID</li>
 *   <li>Atualizar parcialmente um usuário</li>
 *   <li>Atualizar a senha do usuário</li>
 *   <li>Ativar/desativar o acesso de um usuário</li>
 * </ul>
 *
 * @author Você
 * @see User
 * @see UserService
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    /**
     * Retorna todos os usuários cadastrados.
     *
     * @return Lista de {@link User} com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Retorna um usuário específico pelo ID.
     *
     * @param id Identificador do usuário.
     * @return Objeto {@link User} com status 200 OK, ou 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = service.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza parcialmente os dados de um usuário.
     *
     * @param id      Identificador do usuário.
     * @param updates Mapa contendo os campos a serem atualizados.
     * @return Usuário atualizado com status 200 OK, 404 Not Found se não existir, 
     *         ou 500 Internal Server Error em caso de erro interno.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            User updatedUser = service.updateUSer(id, updates);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza a senha de um usuário.
     *
     * @param id   Identificador do usuário.
     * @param body Mapa contendo a nova senha e o ID do usuário que realizou a atualização.
     * @return Usuário com senha atualizada, ou 404 Not Found se não for encontrado.
     */
    @PutMapping("password/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String newPassword = (String) body.get("password");
        Long updatedBy = Long.valueOf(body.get("updated_by").toString());
        Optional<User> updatedUser = service.updatePassword(id, newPassword, updatedBy);
        return updatedUser.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Alterna o acesso do usuário (ativo/inativo).
     *
     * @param id Identificador do usuário.
     * @return Status 204 No Content se alterado com sucesso, ou 404 Not Found se o usuário não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> toggleAccess(@PathVariable Long id) {
        boolean deleted = service.toggleUserAccess(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
