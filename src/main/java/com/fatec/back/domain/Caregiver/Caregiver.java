package com.fatec.back.domain.Caregiver;

import com.fatec.back.domain.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entidade que representa um cuidador (Caregiver) no sistema.
 * <p>
 * Um cuidador está diretamente vinculado a um {@link User} e contém
 * informações adicionais sobre a relação que ele tem com o paciente.
 * A entidade é auditável, contendo metadados de criação e atualização.
 * A chave primária é compartilhada com a entidade {@link User}, utilizando
 * a anotação {@code @MapsId}, caracterizando um relacionamento 1:1.
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code id} - ID do cuidador, herdado do usuário, identificando de forma única o cuidador.</li>
 *     <li>{@code user} - Referência ao usuário correspondente, representando o cuidador no sistema.</li>
 *     <li>{@code relation} - Tipo de relação do cuidador com o paciente, como pai, mãe ou tutor legal.</li>
 *     <li>{@code created_at} - Data de criação do registro, gerenciada automaticamente pelo sistema.</li>
 *     <li>{@code updated_at} - Data da última atualização do registro, gerenciada automaticamente.</li>
 *     <li>{@code created_by} - Usuário responsável pela criação do registro do cuidador.</li>
 *     <li>{@code updated_by} - Usuário responsável pela última atualização do registro.</li>
 *     <li>{@code deleted} - Indicador de exclusão lógica do registro. Se {@code true}, o registro é considerado excluído.</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code user} - Relacionamento de 1:1 com a entidade {@link User} (um cuidador é um usuário).</li>
 *     <li>{@code created_by} e {@code updated_by} - Relacionamento com {@link User}, indicando os responsáveis pela criação e atualização do registro.</li>
 * </ul>
 *
 * @see User
 */
@Entity
@Table(name = "caregiver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Caregiver {
    @Id
    @Column(name = "id_caregiver")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_caregiver")
    private User user;

    @Column(nullable = false)
    private String relation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private User created_by;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updated_by;

    @Column(nullable = false)
    private boolean deleted;

    public Caregiver(String relation){
        this.relation = relation;
    }
}
