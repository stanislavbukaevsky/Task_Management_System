package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на получение нового access-токена
 */
@Data
@Schema(description = "Объект для получения нового access-токена")
public class AccessTokenRequestDto {
    @NotEmpty(message = "Поле refresh-токена не должно быть пустым!")
    @Schema(description = "Refresh-токен пользователя")
    private String refreshToken;
}
