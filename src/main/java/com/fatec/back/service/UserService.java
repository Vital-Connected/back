package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.User.User;
import com.fatec.back.domain.User.UserRequestDTO;
import com.fatec.back.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> updateUser(Long id, UserRequestDTO data) {
        return repository.findById(id).map(existingUser -> {
            existingUser.setEmail(data.email());
            existingUser.setPassword(data.password());
            existingUser.setName(data.name());
            existingUser.setRole(data.role());
            User updatedBy = repository.findById(data.userId())
            .orElseThrow(() -> new RuntimeException("Usuário updated_by não encontrado"));
            existingUser.setUpdatedBy(updatedBy);
            return repository.save(existingUser);
        });
    }

    public Optional<User> updatePassword(Long id, String newPassword, Long UserID) {
        return repository.findById(id).map(user -> {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            User updatedBy = repository.findById(UserID)
            .orElseThrow(() -> new RuntimeException("Usuário updated_by não encontrado"));
            user.setUpdatedBy(updatedBy);
            return repository.save(user);
        });
    }

    public boolean toggleUserAccess(Long id) {
        return repository.findById(id).map(user -> {
            user.setDeleted(!user.isDeleted());
            repository.save(user);
            return true;
        }).orElse(false);
    }
}
