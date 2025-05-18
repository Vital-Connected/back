package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.Role.RoleDTO;
import com.fatec.back.repository.RoleRepository;

/**
 * Serviço responsável pelas operações de negócio relacionadas as roles de usuários.
 * Esta classe contém métodos para manipular dados de roles, como 
 * criação, atualização, obtenção e exclusão de papéis no sistema.
 * 
 * O serviço interage diretamente com o repositório de {@link Role} para realizar 
 * as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllRoles()} - Recupera todos as roles cadastrados no sistema.</li>
 *     <li>{@code getRoleById(Long id)} - Recupera uma role pelo seu ID.</li>
 *     <li>{@code createRole(RoleDTO dto)} - Cria uma nova role no sistema com base nos dados fornecidos.</li>
 *     <li>{@code updateRole(Long id, RoleDTO updatedData)} - Atualiza as informações de uma role existente.</li>
 *     <li>{@code deleteRole(Long id)} - Marca uma role como deletado ou ativa a role novamente.</li>
 * </ul>
 * 
 * @see Role
 * @see RoleDTO
 * @see RoleRepository
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Recupera todos as roles cadastrados no sistema.
     * 
     * @return Lista de todos as roles.
     */
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    /**
     * Recupera uma role pelo seu ID.
     * 
     * @param id ID da role a ser recuperado.
     * @return Um {@link Optional} contendo a role encontrado.
     */
    public Optional<Role> getRoleById(Long id){
        return roleRepository.findById(id);
    }

    /**
     * Cria uma nova role no sistema com base nos dados fornecidos.
     * 
     * @param dto Objeto DTO contendo as informações da role a ser criado.
     * @return A role recém-criado.
     */
    public Role createRole(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.name());
        role.setDescription(dto.description());

        return roleRepository.save(role);
    }

    /**
     * Atualiza as informações de uma role existente.
     * 
     * @param id ID da role a ser atualizado.
     * @param updatedData Dados de atualização fornecidos no DTO.
     * @return O papel atualizado, caso encontrado.
     */
    public Optional<Role> updateRole(Long id, RoleDTO updatedData) {
    return roleRepository.findById(id).map(existing -> {
        if (updatedData.name() != null) {
            existing.setName(updatedData.name());
        }
        if (updatedData.description() != null) {
            existing.setDescription(updatedData.description());
        }
        return roleRepository.save(existing);
    });
    }

     /**
     * Marca uma role como deletado ou ativa o papel novamente.
     * 
     * @param id ID da role a ser deletado ou reativado.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso a role não seja encontrado.
     */
    public boolean deleteRole(Long id) {
        return roleRepository.findById(id).map(role -> {
            role.setDeleted(!role.isDeleted());
            roleRepository.save(role);
            return true;
        }).orElse(false);
    }
}
