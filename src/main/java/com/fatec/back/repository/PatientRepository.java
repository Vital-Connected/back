package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Patient.Patient;

/**
 * Repositório de dados para a entidade {@link Patient}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo operações básicas de CRUD (criar, ler, atualizar, excluir) 
 * para a entidade {@link Patient}, além de outras operações específicas que podem ser definidas conforme necessário.
 * </p>
 * 
 * @see Patient
 * @see JpaRepository
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
