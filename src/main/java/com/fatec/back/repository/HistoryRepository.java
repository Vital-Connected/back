package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.History.History;
/**
 * Repositório de dados para a entidade {@link History}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link History}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see History
 * @see JpaRepository
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    
}
