package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на аутентификацию
 */
@Data
@Schema(description = "Объект для аутентификации пользователя")
public class AuthenticationRequestDto {
    @NotEmpty(message = "Поле электронной почты не должно быть пустым!")
    @Size(min = 6, max = 48, message = "Электронная почта должна содержать от 6 до 48 символов!")
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
}
