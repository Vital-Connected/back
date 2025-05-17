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
