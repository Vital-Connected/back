package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Role.Role;

/**
 * Repositório de dados para a entidade {@link Role}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link Role}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see Role
 * @see JpaRepository
 */
public interface RoleRepository extends JpaRepository <Role, Long> {
    
}
