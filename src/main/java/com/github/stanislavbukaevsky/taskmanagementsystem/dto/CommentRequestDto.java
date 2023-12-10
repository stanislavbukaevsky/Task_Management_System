package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на создание нового комментария к задаче
 */
@Data
@Schema(description = "Объект создания нового комментария к задаче от пользователя")
public class CommentRequestDto {
    @NotEmpty(message = "Поле описания комментария к задаче не должно быть пустым!")
    @Size(min = 6, max = 1000, message = "Описание комментария должно содержать от 6 до 1000 символов!")
    @Schema(description = "Текст комментария")
    private String text;
}
