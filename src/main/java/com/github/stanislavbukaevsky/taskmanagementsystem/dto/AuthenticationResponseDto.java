package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для ответа с информацией об аутентифицированном пользователе
 */
@Data
@Schema(description = "Объект аутентификации для ответа пользователю")
public class AuthenticationResponseDto {
    @Schema(description = "Уникальный идентификатор пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @Schema(description = "Пароль пользователя")
    private String password;
    @Schema(description = "Роль пользователя")
    private String role;
    @Schema(description = "Access токен пользователя")
    private String accessToken;
    @Schema(description = "Refresh токен пользователя")
    private String refreshToken;
    @Schema(description = "Время истечения срока действия access токена")
    private LocalDateTime expiresAtAccess;
    @Schema(description = "Время истечения срока действия refresh токена")
    private LocalDateTime expiresAtRefresh;
}
