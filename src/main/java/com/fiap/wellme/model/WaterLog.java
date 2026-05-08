package com.fiap.wellme.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount_ml", nullable = false)
    private Integer amountMl;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @PrePersist
    public void prePersist() {
        if (this.loggedAt == null) this.loggedAt = LocalDateTime.now();
        if (this.logDate == null) this.logDate = LocalDate.now();
    }
}
