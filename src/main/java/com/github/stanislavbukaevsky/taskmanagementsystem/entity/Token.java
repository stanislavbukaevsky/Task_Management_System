package com.github.stanislavbukaevsky.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс-сущность для всех refresh-токенов
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;
    @Column(name = "date_time_expires")
    private LocalDateTime dateTimeExpires;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
