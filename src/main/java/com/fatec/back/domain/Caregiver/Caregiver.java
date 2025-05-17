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
