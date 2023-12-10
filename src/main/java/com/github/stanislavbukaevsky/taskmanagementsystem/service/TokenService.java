package com.github.stanislavbukaevsky.taskmanagementsystem.service;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Token;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import jakarta.validation.Valid;

/**
 * Сервис-интерфейс для всех refresh-токенов, выданных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface TokenService {
    /**
     * Сигнатура метода для добавления нового refresh-токена в базу данных
     *
     * @param refreshToken refresh-токен
     * @param user         сущность пользователя
     * @param token        сущность токена
     * @return Возвращает добавленный refresh-токен
     */
    Token addToken(String refreshToken, User user, Token token);

    /**
     * Сигнатура метода для выдачи нового access-токена для зарегистрированного пользователя на платформе
     *
     * @param request запрос от пользователя
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     */
    AccessTokenResponseDto replaceAccessToken(@Valid AccessTokenRequestDto request);
}
