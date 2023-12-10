package com.github.stanislavbukaevsky.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на регистрацию
 */
@Data
@Schema(description = "Объект регистрации для запроса от пользователя")
public class RegistrationRequestDto {
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Имя должно содержать от 2 до 64 символов!")
    @Schema(description = "Имя пользователя")
    private String firstName;
    @NotEmpty(message = "Поле фамилии не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Фамилия должна содержать от 2 до 64 символов!")
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @NotEmpty(message = "Поле электронной почты не должно быть пустым!")
    @Size(min = 6, max = 48, message = "Электронная почта должна содержать от 6 до 48 символов!")
    @Schema(description = "Электронная почта пользователя при регистрации, аутентификации и авторизации")
    private String email;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
}
