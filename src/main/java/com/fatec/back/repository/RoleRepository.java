package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Role.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {
    
}
