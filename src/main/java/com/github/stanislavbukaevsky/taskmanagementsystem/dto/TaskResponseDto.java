package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для ответа пользователю с полной информацией о задачи
 */
@Data
@Schema(description = "Объект задачи для ответа пользователю")
public class TaskResponseDto {
    @Schema(description = "Уникальный идентификатор задачи")
    private Long id;
    @Schema(description = "Заголовок задачи")
    private String heading;
    @Schema(description = "Описание задачи")
    private String description;
    @Schema(description = "Дата и время создания задачи")
    private LocalDateTime dateTime;
    @Schema(description = "Статус задачи")
    private String status;
    @Schema(description = "Приоритет задачи")
    private String priority;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @Schema(description = "Роль пользователя")
    private String role;
}
