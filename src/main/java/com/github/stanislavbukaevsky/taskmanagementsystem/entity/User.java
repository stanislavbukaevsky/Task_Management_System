package com.github.stanislavbukaevsky.taskmanagementsystem.entity;

import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс-сущность для всех пользователей
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Task> authorTask;
    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    private List<Task> executorTask;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Token token;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
