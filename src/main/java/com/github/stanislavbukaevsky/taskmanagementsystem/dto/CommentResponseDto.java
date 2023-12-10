package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для ответа пользователю с полной информацией о комментарии к задаче
 */
@Data
@Schema(description = "Объект комментария к задаче для ответа пользователю")
public class CommentResponseDto {
    @Schema(description = "Уникальный идентификатор комментария")
    private Long id;
    @Schema(description = "Текст комментария")
    private String text;
    @Schema(description = "Дата и время создания комментария")
    private LocalDateTime dateTime;
    @Schema(description = "Заголовок задачи")
    private String headingTask;
    @Schema(description = "Описание задачи")
    private String descriptionTask;
    @Schema(description = "Статус задачи")
    private String statusTask;
    @Schema(description = "Приоритет задачи")
    private String priorityTask;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @Schema(description = "Роль пользователя")
    private String role;
}
