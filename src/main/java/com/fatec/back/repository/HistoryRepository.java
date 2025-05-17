package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.back.domain.History.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    
}
