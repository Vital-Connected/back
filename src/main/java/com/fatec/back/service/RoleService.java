package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.Role.RoleDTO;
import com.fatec.back.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id){
        return roleRepository.findById(id);
    }

    public Role createRole(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.name());
        role.setDescription(dto.description());

        return roleRepository.save(role);
    }

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


    public boolean deleteRole(Long id) {
        return roleRepository.findById(id).map(role -> {
            role.setDeleted(!role.isDeleted());
            roleRepository.save(role);
            return true;
        }).orElse(false);
    }
}
