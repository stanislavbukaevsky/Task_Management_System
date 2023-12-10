package com.github.stanislavbukaevsky.taskmanagementsystem.entity;

import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-сущность для всех задач
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "heading")
    private String heading;
    @Column(name = "description")
    private String description;
    @Column(name = "date_and_time")
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;
    @OneToMany(mappedBy = "task", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Comment> comments;
}
