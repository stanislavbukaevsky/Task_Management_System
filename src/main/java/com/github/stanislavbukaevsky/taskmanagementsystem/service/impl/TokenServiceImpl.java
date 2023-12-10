package com.github.stanislavbukaevsky.taskmanagementsystem.service.impl;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Token;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.AuthenticationUsersException;
import com.github.stanislavbukaevsky.taskmanagementsystem.mapper.TokenMapper;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.TokenRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurity;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurityService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.TokenService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.UserService;
import com.github.stanislavbukaevsky.taskmanagementsystem.token.TokenDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.REPLACE_ACCESS_AND_REFRESH_TOKEN_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой всех refresh-токенов, выданных на платформе.
 * Реализует интерфейс {@link TokenService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;
    private final TokenDetailsService tokenDetailsService;

    /**
     * Реализация метода для добавления нового refresh-токена в базу данных
     *
     * @param refreshToken refresh-токен
     * @param user         сущность пользователя
     * @param token        сущность токена
     * @return Возвращает добавленный refresh-токен
     */
    @Override
    public Token addToken(String refreshToken, User user, Token token) {
        LocalDateTime dateTimeCreation = LocalDateTime.now();
        LocalDateTime dateTimeExpires = LocalDateTime.ofInstant(
                        tokenDetailsService.getRefreshExpiration().toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.MINUTES);
        token.setRefreshToken(refreshToken);
        token.setDateTimeCreation(dateTimeCreation);
        token.setDateTimeExpires(dateTimeExpires);
        token.setUser(user);
        log.info(ADD_TOKEN_MESSAGE_LOGGER_SERVICE);
        return tokenRepository.save(token);
    }

    /**
     * Реализация метода для выдачи нового access-токена для зарегистрированного пользователя на платформе
     *
     * @param request запрос от пользователя
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     */
    @Override
    public AccessTokenResponseDto replaceAccessToken(@Valid AccessTokenRequestDto request) {
        if (tokenDetailsService.validateRefreshToken(request.getRefreshToken())) {
            final Claims claims = tokenDetailsService.getRefreshClaims(request.getRefreshToken());
            final String email = claims.getSubject();
            final String savedRefreshToken = findRefreshTokenByEmail(email);
            if (savedRefreshToken != null && savedRefreshToken.equals(request.getRefreshToken())) {
                UserSecurity userSecurity = (UserSecurity) userSecurityService.loadUserByUsername(email);
                final String emailUser = userSecurity.getUser().getEmail();
                final String accessToken = tokenDetailsService.generateAccessToken(userSecurity);
                final LocalDateTime dateTimeExpiresAccess = LocalDateTime.ofInstant(
                        tokenDetailsService.getAccessExpiration().toInstant(), ZoneId.systemDefault());
                log.info(REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE, request.getRefreshToken());
                return tokenMapper.toAccessTokenResponse(emailUser, accessToken, dateTimeExpiresAccess);
            }
        }
        throw new AuthenticationUsersException(REPLACE_ACCESS_AND_REFRESH_TOKEN_MESSAGE_EXCEPTION_SERVICE);
    }

    /**
     * Приватный метод для поиска refresh-токена по электронной почте пользователя
     *
     * @param email электронная почта пользователя
     * @return Возвращает найденный refresh-токен
     */
    private String findRefreshTokenByEmail(String email) {
        User user = userService.findUserByEmail(email);
        log.info(FIND_REFRESH_TOKEN_BY_EMAIL_MESSAGE_LOGGER_SERVICE, email);
        return user.getToken().getRefreshToken();
    }
}
