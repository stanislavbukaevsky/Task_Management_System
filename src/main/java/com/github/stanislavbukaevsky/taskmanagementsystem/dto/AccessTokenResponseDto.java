package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения информации о пользователе и получении access-токена
 */
@Data
@Schema(description = "Объект для получения нового access-токена")
public class AccessTokenResponseDto {
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @Schema(description = "Access токен пользователя")
    private String accessToken;
    @Schema(description = "Время истечения срока действия access токена")
    private LocalDateTime expiresAtAccess;
}
