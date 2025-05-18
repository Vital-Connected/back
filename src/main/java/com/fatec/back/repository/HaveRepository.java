package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Have.Have;

/**
 * Repositório de dados para a entidade {@link Have}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link Have}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see Have
 * @see JpaRepository
 */
public interface HaveRepository extends JpaRepository<Have, Long>{
    
}
