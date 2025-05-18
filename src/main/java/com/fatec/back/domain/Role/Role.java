package com.fatec.back.domain.Role;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa o papel ou função atribuída a um usuário no sistema.
 * <p>
 * A classe {@code Role} define os papéis que podem ser atribuídos aos usuários do sistema.
 * Cada papel contém um nome e uma descrição, além de informações sobre a criação e
 * a última atualização do registro. O campo {@code deleted} é utilizado para indicar 
 * se o papel foi deletado (soft delete).
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code id} - Identificador único do papel no banco de dados.</li>
 *     <li>{@code name} - Nome do papel (por exemplo, "ADMIN", "USER").</li>
 *     <li>{@code description} - Descrição detalhada do papel.</li>
 *     <li>{@code created_at} - Data e hora de criação do papel.</li>
 *     <li>{@code updated_at} - Data e hora da última atualização do papel.</li>
 *     <li>{@code deleted} - Flag que indica se o papel foi deletado (soft delete).</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>Esta classe pode ser associada com a classe {@link User} para definir o papel de cada usuário no sistema.</li>
 * </ul>
 *
 * @see User
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;
    private String name;
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(nullable = false)
    private boolean deleted = false;
    
    public Role(String name, String description){
        this.name = name;
        this.description = description;
    }

}
