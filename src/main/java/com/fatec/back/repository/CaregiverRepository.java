package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Caregiver.Caregiver;

/**
 * Repositório de dados para a entidade {@link Caregiver}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link Caregiver}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see Caregiver
 * @see JpaRepository
 */
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    
}
