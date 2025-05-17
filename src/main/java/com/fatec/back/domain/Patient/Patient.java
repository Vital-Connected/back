package com.fatec.back.domain.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fatec.back.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

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
    
    public Patient(LocalDate  birthday, String patientCondition){
        this.birthday = birthday;
        this.patientCondition = patientCondition;
    }
}
