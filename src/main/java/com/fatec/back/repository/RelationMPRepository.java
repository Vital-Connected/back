package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.RelationMP.RelationMPDTO;

public interface RelationMPRepository extends JpaRepository<RelationMPDTO, Long> {
    
}
