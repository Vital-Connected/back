package com.fatec.back.domain.History;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.RelationMP.RelationMP;
import com.fatec.back.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade que representa o histórico de administração de medicamentos ou ações relacionadas a um paciente.
 * <p>
 * Esta classe mantém o registro de se o medicamento foi administrado e o momento dessa administração.
 * Além disso, registra os dados relacionados à criação, atualização e exclusão lógica do histórico de administração.
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code id} - Identificador único do histórico de administração.</li>
 *     <li>{@code taked} - Indica se o medicamento foi administrado (true) ou não (false).</li>
 *     <li>{@code takedAt} - Data e hora de quando o medicamento foi administrado, se aplicável.</li>
 *     <li>{@code relationMP} - Relação entre o medicamento e o paciente, representada pela entidade {@link RelationMP}.</li>
 *     <li>{@code createdAt} - Data e hora de criação do registro, gerenciada automaticamente pelo sistema.</li>
 *     <li>{@code updatedAt} - Data e hora da última atualização do registro, gerenciada automaticamente.</li>
 *     <li>{@code createdBy} - Usuário responsável pela criação deste histórico de administração.</li>
 *     <li>{@code updatedBy} - Usuário responsável pela última atualização deste histórico.</li>
 *     <li>{@code deleted} - Flag indicando se o registro foi excluído (soft delete).</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code relationMP} - Relacionamento com a entidade {@link RelationMP}, indicando a associação entre o medicamento e o paciente.</li>
 *     <li>{@code createdBy} e {@code updatedBy} - Relacionamento com a entidade {@link User}, indicando os usuários responsáveis pela criação e atualização do histórico.</li>
 * </ul>
 *
 * @see RelationMP
 * @see User
 */

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history")
    private Long id;

    @Column(nullable = false)
    private boolean taked;

    @Column(nullable = true)
    private LocalDateTime takedAt;

    @ManyToOne
    @JoinColumn(name = "id_relation_mp", nullable = false)
    private RelationMP relationMP;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(nullable = false)
    private boolean deleted = false;

    public History(Boolean taked, LocalDateTime takedAt) {
        this.taked = taked;
        this.takedAt = takedAt;
    }
}
