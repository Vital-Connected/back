package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.fatec.back.domain.User.User;

/**
 * Repositório de dados para a entidade {@link User}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link User}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see User
 * @see JpaRepository
 */
public interface UserRepository extends JpaRepository <User, Long>{
    UserDetails findByEmail(String email);
    
}
