package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Caregiver.Caregiver;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    
}
