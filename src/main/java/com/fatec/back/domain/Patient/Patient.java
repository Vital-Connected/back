package com.fatec.back.domain.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/**
 * Representa um paciente no sistema.
 * <p>
 * A classe {@code Patient} contém informações detalhadas sobre um paciente, incluindo sua data 
 * de nascimento, condição médica e os usuários responsáveis por seu gerenciamento. Um paciente 
 * está associado a um usuário (entidade {@link User}) através do campo {@code user}, e pode ter 
 * um histórico médico e status de deletação.
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code id} - Identificador único do paciente.</li>
 *     <li>{@code user} - O usuário associado a este paciente, mapeado como uma relação 1-para-1.</li>
 *     <li>{@code birthday} - Data de nascimento do paciente.</li>
 *     <li>{@code patientCondition} - Condição médica do paciente.</li>
 *     <li>{@code createdAt} - Data e hora de criação do registro do paciente.</li>
 *     <li>{@code updatedAt} - Data e hora da última atualização do registro do paciente.</li>
 *     <li>{@code createdBy} - Usuário responsável pela criação do registro, referenciado pela entidade {@link User}.</li>
 *     <li>{@code updatedBy} - Usuário responsável pela última atualização do registro, referenciado pela entidade {@link User}.</li>
 *     <li>{@code deleted} - Flag que indica se o paciente foi deletado (soft delete).</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code user} - Relacionamento 1-para-1 com a entidade {@link User}, representando o usuário associado a este paciente.</li>
 *     <li>{@code createdBy} e {@code updatedBy} - Relacionamentos com a entidade {@link User}, representando os usuários responsáveis pelas operações de criação e atualização, respectivamente.</li>
 * </ul>
 *
 * @see User
 */
public class Patient {
    @Id
    @Column(name = "id_patient")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_patient")
    private User user;

    private LocalDate birthday;

    @Column(name = "patient_condition")
    private String patientCondition;

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
    private boolean deleted = false;
    
    public Patient(LocalDate birthday, String patientCondition){
        this.birthday = birthday;
        this.patientCondition = patientCondition;
    }
}
