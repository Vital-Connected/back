package com.fatec.back.domain.Have;

import java.time.LocalDate;
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
 * Entidade que representa o relacionamento entre um paciente e seu cuidador,
 * indicando o período de acompanhamento e os detalhes relacionados à criação e
 * atualização desse vínculo.
 * <p>
 * A classe estabelece um vínculo entre o paciente e o cuidador, incluindo as
 * datas de início e término (quando aplicável) do acompanhamento. Ela também
 * possui informações sobre a criação e atualização do vínculo, com metadados
 * auditáveis.
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code startDate} - Data de início do vínculo entre o paciente e o cuidador.</li>
 *     <li>{@code endDate} - Data de término do vínculo (pode ser nula se o vínculo estiver em andamento).</li>
 *     <li>{@code patient} - Referência ao paciente associado a este vínculo.</li>
 *     <li>{@code caregiver} - Referência ao cuidador associado a este vínculo.</li>
 *     <li>{@code createdAt} - Data e hora em que o vínculo foi criado, gerenciada automaticamente pelo sistema.</li>
 *     <li>{@code updatedAt} - Data e hora da última atualização do vínculo, gerenciada automaticamente.</li>
 *     <li>{@code createdBy} - Usuário responsável pela criação deste vínculo.</li>
 *     <li>{@code updatedBy} - Usuário responsável pela última atualização deste vínculo.</li>
 *     <li>{@code deleted} - Flag indicando se o vínculo foi deletado (soft delete).</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code patient} - Relacionamento com a entidade {@link Patient}, indicando qual paciente está vinculado ao cuidador.</li>
 *     <li>{@code caregiver} - Relacionamento com a entidade {@link Caregiver}, indicando qual cuidador está associado ao paciente.</li>
 *     <li>{@code createdBy} e {@code updatedBy} - Relacionamento com {@link User}, indicando os responsáveis pela criação e atualização do vínculo.</li>
 * </ul>
 *
 * @see Patient
 * @see Caregiver
 * @see User
 */
@Entity
@Table(name = "have")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Have {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_have")
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_caregiver", nullable = false)
    private Caregiver caregiver;

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

    public Have(LocalDate startDate, LocalDate endDate, Patient patient, Caregiver caregiver) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.patient = patient;
        this.caregiver = caregiver;
    }
}
