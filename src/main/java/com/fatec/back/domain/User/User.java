package com.fatec.back.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fatec.back.domain.Role.Role;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um usuário do sistema e implementa a interface {@link UserDetails},
 * o que permite que a classe seja usada diretamente pelo Spring Security para autenticação
 * e autorização.
 * <p>
 * A classe {@code User} contém informações essenciais sobre o usuário, como e-mail, senha,
 * nome e papel ({@code role}). Ela também gerencia a criação e atualização do usuário com
 * campos de auditoria. A flag {@code deleted} indica se o usuário foi excluído logicamente
 * (soft delete).
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code email} - E-mail único do usuário, utilizado para autenticação no sistema.</li>
 *     <li>{@code password} - Senha associada ao usuário para autenticação.</li>
 *     <li>{@code name} - Nome completo do usuário.</li>
 *     <li>{@code role} - Papel atribuído ao usuário, determinando as permissões e acessos.</li>
 *     <li>{@code createdAt} - Data e hora em que o usuário foi criado no sistema.</li>
 *     <li>{@code updatedAt} - Data e hora da última atualização do registro do usuário.</li>
 *     <li>{@code createdBy} - Usuário que criou o registro desse usuário.</li>
 *     <li>{@code updatedBy} - Usuário que realizou a última atualização do registro desse usuário.</li>
 *     <li>{@code deleted} - Flag que indica se o usuário foi logicamente excluído (soft delete).</li>
 * </ul>
 *
 * Métodos:
 * <ul>
 *     <li>{@code getAuthorities()} - Retorna as permissões associadas ao papel do usuário, como {@code ROLE_ADMIN} ou {@code ROLE_USER}.</li>
 *     <li>{@code getUsername()} - Retorna o e-mail do usuário, usado para autenticação.</li>
 *     <li>{@code isAccountNonExpired()} - Verifica se a conta do usuário está expirada. Sempre retorna {@code true}, pois a expiração não é gerenciada.</li>
 *     <li>{@code isAccountNonLocked()} - Verifica se a conta do usuário está bloqueada. Sempre retorna {@code true}, pois o bloqueio não é gerenciado.</li>
 *     <li>{@code isCredentialsNonExpired()} - Verifica se as credenciais do usuário estão expiradas. Sempre retorna {@code true}, pois as credenciais não expiram.</li>
 *     <li>{@code isEnabled()} - Verifica se a conta do usuário está habilitada. Retorna {@code false} se o usuário estiver deletado.</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code Role} - Um usuário tem um papel ({@code role}) que define suas permissões e acessos no sistema.</li>
 * </ul>
 *
 * @see Role
 * @see UserDetails
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id_role")
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(nullable = false)
    private boolean deleted = false;

    public User(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getName().toUpperCase()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.deleted;
    }
}
