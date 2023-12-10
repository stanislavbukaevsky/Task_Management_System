package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на создание новой задачи
 */
@Data
@Schema(description = "Объект создания новой задачи от пользователя")
public class TaskRequestDto {
    @NotEmpty(message = "Поле заголовка задачи не должно быть пустым!")
    @Size(min = 2, max = 128, message = "Заголовок задачи должен содержать от 2 до 128 символов!")
    @Schema(description = "Заголовок задачи")
    private String heading;
    @NotEmpty(message = "Поле описания задачи не должно быть пустым!")
    @Size(min = 6, max = 1000, message = "Описание задачи должно содержать от 6 до 1000 символов!")
    @Schema(description = "Описание задачи")
    private String description;
}
