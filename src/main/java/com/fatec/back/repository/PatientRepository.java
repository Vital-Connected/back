package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Patient.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
