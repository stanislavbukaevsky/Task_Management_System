package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для ответа пользователю с его полной информацией
 */
@Data
@Schema(description = "Объект регистрации для ответа пользователю")
public class RegistrationResponseDto {
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
}
