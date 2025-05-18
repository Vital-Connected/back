package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.RelationMP.RelationMP;

/**
 * Repositório de dados para a entidade {@link RelationMP}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link RelationMP}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see RelationMP
 * @see JpaRepository
 */
public interface RelationMPRepository extends JpaRepository<RelationMP, Long> {
    
}
