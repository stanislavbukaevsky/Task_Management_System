package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationResponseDto;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

/**
 * Маппер-интерфейс, который преобразует информацию о пользователе, зарегистрированном на платформе в DTO
 */
@Mapper
public interface TokenMapper {
    /**
     * Этот метод преобразует полученные поля в DTO, для получения информации об аутентифицированном пользователе
     *
     * @param id               уникальный идентификатор пользователя
     * @param firstName        имя пользователя
     * @param lastName         фамилия пользователя
     * @param email            электронная почта пользователя
     * @param password         пароль пользователя
     * @param role             роль пользователя
     * @param accessToken      access токен пользователя
     * @param refreshToken     refresh токен пользователя
     * @param expiresAtAccess  дата истечения срока действия access токена
     * @param expiresAtRefresh дата истечения срока действия refresh токена
     * @return Возвращает сформированную DTO с ответом об аутентифицированном пользователе
     */
    AuthenticationResponseDto toAuthenticationResponse(Long id,
                                                       String firstName,
                                                       String lastName,
                                                       String email,
                                                       String password,
                                                       String role,
                                                       String accessToken,
                                                       String refreshToken,
                                                       LocalDateTime expiresAtAccess,
                                                       LocalDateTime expiresAtRefresh);

    /**
     * Этот метод преобразует полученные поля в DTO, для получения информации о новом access-токене
     *
     * @param email           электронная почта пользователя
     * @param accessToken     access токен пользователя
     * @param expiresAtAccess дата истечения срока действия access токена
     * @return Возвращает сформированную DTO с ответом о новом access-токене
     */
    AccessTokenResponseDto toAccessTokenResponse(String email, String accessToken, LocalDateTime expiresAtAccess);
}
