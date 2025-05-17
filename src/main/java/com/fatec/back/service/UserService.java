package com.fatec.back.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.User.User;
import com.fatec.back.domain.User.UserRequestDTO;
import com.fatec.back.repository.RoleRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

   public User updateUSer(Long id, Map<String, Object> updates) {
    User user = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (updates.containsKey("email")) {
        Object emailObj = updates.get("email");
        if (emailObj instanceof String email && !email.isBlank()) {
            user.setEmail(email);
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    if (updates.containsKey("name")) {
        Object nameObj = updates.get("name");
        if (nameObj instanceof String name && !name.isBlank()) {
            user.setName(name);
        } else {
            throw new IllegalArgumentException("Nome inválido.");
        }
    }

    if (updates.containsKey("password")) {
        Object passwordObj = updates.get("password");
        if (passwordObj instanceof String password && password.length() >= 6) {
            user.setPassword(password);
        } else {
            throw new IllegalArgumentException("Senha inválida. Deve ter ao menos 6 caracteres.");
        }
    }

    if (updates.containsKey("role")) {
        try {
            Long roleId = Long.valueOf(updates.get("role").toString());
            Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
            user.setRole(role);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de role inválido.");
        }
    }

    return repository.save(user);
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
