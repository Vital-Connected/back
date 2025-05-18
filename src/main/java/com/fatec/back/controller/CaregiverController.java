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

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Caregiver.CaregiverDTO;
import com.fatec.back.service.CaregiverService;

/**
 * Controlador REST responsável pelas operações da entidade {@link Caregiver}.
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
 * @author Você
 * @see Caregiver
 * @see CaregiverDTO
 * @see CaregiverService
 */
@RestController
@RequestMapping("/caregiver")
public class CaregiverController {
    @Autowired
    private CaregiverService service;

    /**
     * Retorna todos os cuidadores cadastrados no sistema.
     *
     * @return Lista de objetos {@link Caregiver} em formato de resposta HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Caregiver>> getAll() {
        return ResponseEntity.ok(service.getAllCaregivers());
    }

    /**
     * Busca um cuidador específico pelo seu ID.
     *
     * @param id ID do cuidador a ser buscado.
     * @return O cuidador encontrado com status 200 OK, ou 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Caregiver> getById(@PathVariable Long id) {
        Optional<Caregiver> dto = service.getCaregiverById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo cuidador com base nos dados fornecidos.
     *
     * @param dto Objeto com os dados necessários para criação do cuidador.
     * @return Cuidador criado com status 200 OK.
     * 
     * @see CaregiverDTO
     */
    @PostMapping
    public ResponseEntity<Caregiver> create(@RequestBody CaregiverDTO dto) {
        System.out.println(dto);
        Caregiver saved = service.createCaregiver(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza um cuidador existente com base no ID fornecido.
     *
     * @param id   ID do cuidador a ser atualizado.
     * @param body Objeto {@link CaregiverDTO} contendo os novos dados.
     * @return Cuidador atualizado com status 200 OK, ou 404 Not Found se o cuidador não existir.
     * 
     * @see CaregiverDTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Caregiver> update(@PathVariable Long id, @RequestBody CaregiverDTO body) {
        Optional<Caregiver> updated = service.updateCaregiver(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica (soft delete) de um cuidador com base no ID.
     * O ID do usuário que realizou a ação deve ser fornecido no corpo da requisição.
     *
     * @param id   ID do cuidador a ser excluído.
     * @param body Mapa contendo a chave {@code userId} com o ID do usuário responsável pela exclusão.
     * @return Resposta HTTP 204 No Content se a exclusão for bem-sucedida,
     *         ou 404 Not Found se o cuidador não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deleteCaregiver(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
