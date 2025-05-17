package com.fatec.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.fatec.back.domain.User.User;

public interface UserRepository extends JpaRepository <User, Long>{
    UserDetails findByEmail(String email);
    
}
