package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Medication.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
}
