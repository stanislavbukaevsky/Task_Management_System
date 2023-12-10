package com.github.stanislavbukaevsky.taskmanagementsystem.service;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationResponseDto;
import jakarta.validation.Valid;

/**
 * Сервис-интерфейс для регистрации и аутентификации пользователей на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param request класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    RegistrationResponseDto registration(@Valid RegistrationRequestDto request);

    /**
     * Сигнатура метода аутентификации пользователей на платформе
     *
     * @param request класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     */
    AuthenticationResponseDto authentication(@Valid AuthenticationRequestDto request);
}
