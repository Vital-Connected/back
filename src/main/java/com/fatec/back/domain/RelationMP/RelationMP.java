package com.fatec.back.domain.RelationMP;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Representa a relação entre um medicamento e um paciente, incluindo detalhes sobre 
 * a dosagem, frequência de administração e o período do tratamento.
 * <p>
 * A classe {@code RelationMP} é responsável por armazenar as informações relacionadas 
 * à administração de um medicamento para um paciente. Ela contém dados sobre a dosagem, 
 * frequência, o intervalo de tempo do tratamento, o medicamento prescrito, o paciente, 
 * além de informações de auditoria sobre a criação e atualização do registro.
 * </p>
 *
 * Campos:
 * <ul>
 *     <li>{@code dosage} - A dosagem do medicamento a ser administrada.</li>
 *     <li>{@code frequencyValue} - O valor da frequência (ex: 2, 3, etc.).</li>
 *     <li>{@code frequencyUnit} - A unidade de frequência, que pode ser horas, dias ou semanas.</li>
 *     <li>{@code totalDosage} - A dosagem total do medicamento durante o período do tratamento.</li>
 *     <li>{@code medication} - O medicamento prescrito para o paciente, representado pela entidade {@link Medication}.</li>
 *     <li>{@code patient} - O paciente a quem o medicamento é prescrito, representado pela entidade {@link Patient}.</li>
 *     <li>{@code startDate} - Data de início do tratamento com o medicamento.</li>
 *     <li>{@code endDate} - Data de término do tratamento com o medicamento.</li>
 *     <li>{@code createdAt} - Data e hora de criação do registro de relação entre paciente e medicamento.</li>
 *     <li>{@code updatedAt} - Data e hora da última atualização do registro.</li>
 *     <li>{@code createdBy} - O usuário responsável pela criação do registro, representado pela entidade {@link User}.</li>
 *     <li>{@code updatedBy} - O usuário responsável pela última atualização do registro, representado pela entidade {@link User}.</li>
 *     <li>{@code deleted} - Flag que indica se o registro foi deletado (soft delete).</li>
 * </ul>
 *
 * Enum:
 * <ul>
 *     <li>{@code FrequencyUnit} - Unidade de frequência, podendo ser horas, dias ou semanas.</li>
 * </ul>
 *
 * Relacionamentos:
 * <ul>
 *     <li>{@code medication} - Relacionamento com a entidade {@link Medication}, representando o medicamento prescrito.</li>
 *     <li>{@code patient} - Relacionamento com a entidade {@link Patient}, representando o paciente que está sendo tratado.</li>
 *     <li>{@code createdBy} e {@code updatedBy} - Relacionamentos com a entidade {@link User}, representando os usuários responsáveis pela criação e atualização do registro.</li>
 * </ul>
 *
 * @see Medication
 * @see Patient
 * @see User
 */
@Entity
@Table(name = "relations_mp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class RelationMP {
        public enum FrequencyUnit {
        HOURS,
        DAYS,
        WEEKS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relation_mp")
    private Long id;

    @Column(nullable = false)
    private Integer dosage;

    @Column(name = "frequency_value", nullable = false)
    private Integer frequencyValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_unit", nullable = false)
    private FrequencyUnit frequencyUnit;

    @Column(name = "total_dosage", nullable = false)
    private Integer totalDosage;

    @ManyToOne
    @JoinColumn(name = "id_medication", nullable = false)
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

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

    public RelationMP(int dosage,int frequencyValue,FrequencyUnit frequencyUnit,int totalDosage,Medication medication,Patient patient,LocalDate startDate,LocalDate endDate) {
    this.dosage = dosage;
    this.frequencyValue = frequencyValue;
    this.frequencyUnit = frequencyUnit;
    this.totalDosage = totalDosage;
    this.medication = medication;
    this.patient = patient;
    this.startDate = startDate;
    this.endDate = endDate;
    }

}
