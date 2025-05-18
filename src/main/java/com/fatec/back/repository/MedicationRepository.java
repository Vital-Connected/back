package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Medication.Medication;

/**
 * Repositório de dados para a entidade {@link Medication}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link Medication}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see Medication
 * @see JpaRepository
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
}
