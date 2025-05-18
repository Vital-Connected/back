package com.fatec.back.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.RoleRepository;
import com.fatec.back.repository.UserRepository;

/**
 * Serviço responsável pelas operações de negócio relacionadas aos usuários.
 * Esta classe contém métodos para manipular dados de usuários, como 
 * obtenção, atualização, troca de senha e alteração do status de acesso.
 * 
 * O serviço interage diretamente com os repositórios de {@link User} e {@link Role} 
 * para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllUsers()} - Recupera todos os usuários do sistema.</li>
 *     <li>{@code getUserById(Long id)} - Recupera um usuário pelo seu ID.</li>
 *     <li>{@code updateUser(Long id, Map<String, Object> updates)} - Atualiza informações de um usuário com base nos dados fornecidos.</li>
 *     <li>{@code updatePassword(Long id, String newPassword, Long UserID)} - Atualiza a senha de um usuário.</li>
 *     <li>{@code toggleUserAccess(Long id)} - Altera o status de acesso de um usuário, ativando ou desativando sua conta.</li>
 * </ul>
 * 
 * @see User
 * @see Role
 * @see UserRepository
 * @see RoleRepository
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private RoleRepository roleRepository;

     /**
     * Recupera todos os usuários cadastrados no sistema.
     * 
     * @return Lista de todos os usuários.
     */
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * Recupera um usuário pelo seu ID.
     * 
     * @param id ID do usuário a ser recuperado.
     * @return Um {@link Optional} contendo o usuário encontrado.
     */
    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    /**
     * Atualiza as informações de um usuário com os dados fornecidos.
     * 
     * @param id ID do usuário a ser atualizado.
     * @param updates Mapa contendo os dados a serem atualizados.
     * @return O usuário atualizado.
     * @throws IllegalArgumentException Se algum dado fornecido for inválido.
     */
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


    /**
     * Atualiza a senha de um usuário.
     * 
     * @param id ID do usuário.
     * @param newPassword Nova senha a ser definida.
     * @param UserID ID do usuário que está realizando a atualização.
     * @return O usuário com a senha atualizada.
     */
    public Optional<User> updatePassword(Long id, String newPassword, Long UserID) {
        return repository.findById(id).map(user -> {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            User updatedBy = repository.findById(UserID)
            .orElseThrow(() -> new RuntimeException("Usuário updated_by não encontrado"));
            user.setUpdatedBy(updatedBy);
            return repository.save(user);
        });
    }

    /**
     * Altera o status de acesso de um usuário, ativando ou desativando sua conta.
     * 
     * @param id ID do usuário.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso o usuário não seja encontrado.
     */
    public boolean toggleUserAccess(Long id) {
        return repository.findById(id).map(user -> {
            user.setDeleted(!user.isDeleted());
            repository.save(user);
            return true;
        }).orElse(false);
    }
}
