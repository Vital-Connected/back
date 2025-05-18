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

import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.Patient.PatientDTO;
import com.fatec.back.service.PatientService;

/**
 * Controlador REST para operações relacionadas à entidade {@link Patient}.
 *
 * Este controlador fornece endpoints para:
 * <ul>
 *   <li>Listar todos os pacientes</li>
 *   <li>Buscar paciente por ID</li>
 *   <li>Criar um novo paciente</li>
 *   <li>Atualizar dados de um paciente existente</li>
 *   <li>Realizar exclusão lógica (soft delete) de um paciente</li>
 * </ul>
 *
 * @author Você
 * @see Patient
 * @see PatientDTO
 * @see PatientService
 */
@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService service;

     /**
     * Retorna todos os pacientes cadastrados.
     *
     * @return Lista de {@link Patient} com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {
        return ResponseEntity.ok(service.getAllPatients());
    }

    /**
     * Retorna um paciente pelo ID fornecido.
     *
     * @param id Identificador do paciente.
     * @return {@link Patient} com status 200 OK, ou 404 Not Found se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        Optional<Patient> dto = service.getPatientById(id);
        return dto.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo paciente a partir dos dados informados.
     *
     * @param dto Objeto {@link PatientDTO} com os dados do paciente.
     * @return Paciente criado com status 200 OK.
     */
    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody PatientDTO dto) {
        System.out.println(dto);
        Patient saved = service.createPatient(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Atualiza os dados de um paciente existente.
     *
     * @param id   Identificador do paciente a ser atualizado.
     * @param body Objeto {@link PatientDTO} com os novos dados.
     * @return Paciente atualizado com status 200 OK, ou 404 Not Found se não existir.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody PatientDTO body) {
        Optional<Patient> updated = service.updatePatient(id, body);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a exclusão lógica de um paciente (soft delete).
     *
     * @param id   Identificador do paciente a ser excluído.
     * @param body Mapa contendo o campo {@code userId} que representa o usuário que realiza a exclusão.
     * @return Status 204 No Content se a exclusão for bem-sucedida, ou 404 Not Found caso contrário.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Number userIdNumber = (Number) body.get("userId");
        Long userId = userIdNumber.longValue();
        boolean deleted = service.deletePatient(id, userId);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
