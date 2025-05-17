package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.Have.Have;

public interface HaveRepository extends JpaRepository<Have, Long>{
    
}
