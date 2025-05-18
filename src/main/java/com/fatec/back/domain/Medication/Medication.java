package com.fatec.back.domain.Medication;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Patient.Patient;
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
 * Entidade que representa um medicamento no sistema.
 * <p>
 * A classe {@code Medication} armazena informações sobre um medicamento, incluindo seu nome, 
 * a função do medicamento e informações de auditoria, como quando foi criado e atualizado, 
 * e quem realizou essas operações. A classe também mantém o estado do medicamento (se foi 
 * deletado ou não).
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code id} - Identificador único do medicamento.</li>
 *     <li>{@code name} - Nome do medicamento.</li>
 *     <li>{@code medicationFunction} - Função ou propósito do medicamento.</li>
 *     <li>{@code createdAt} - Data e hora de criação do medicamento.</li>
 *     <li>{@code updatedAt} - Data e hora de atualização do medicamento.</li>
 *     <li>{@code createdBy} - Usuário responsável pela criação do medicamento, referenciado pela entidade {@link User}.</li>
 *     <li>{@code updatedBy} - Usuário responsável pela última atualização do medicamento, referenciado pela entidade {@link User}.</li>
 *     <li>{@code deleted} - Flag que indica se o medicamento foi deletado (soft delete).</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code createdBy} e {@code updatedBy} - Relacionamentos com a entidade {@link User}, representando os usuários responsáveis pelas operações de criação e atualização, respectivamente.</li>
 * </ul>
 *
 * @see User
 */
@Entity
@Table(name = "medication")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medication")
    private Long id;

    private String name;

    @Column(name = "medication_function")
    private String medicationFunction;

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

    public Medication(String name, String medicationFunction) {
        this.name = name;
        this.medicationFunction = medicationFunction;
    }
}
