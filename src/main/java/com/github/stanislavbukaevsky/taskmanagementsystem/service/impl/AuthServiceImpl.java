package com.github.stanislavbukaevsky.taskmanagementsystem.service.impl;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Token;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Role;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.AuthenticationUsersException;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.taskmanagementsystem.mapper.TokenMapper;
import com.github.stanislavbukaevsky.taskmanagementsystem.mapper.UserMapper;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.TokenRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.UserRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurity;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurityService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.AuthService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.TokenService;
import com.github.stanislavbukaevsky.taskmanagementsystem.token.TokenDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.AUTHENTICATION_USERS_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.REGISTRATION_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для регистрации и аутентификации пользователей на платформе.
 * Реализует интерфейс {@link AuthService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityService userSecurityService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenDetailsService tokenDetailsService;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final TokenMapper tokenMapper;

    /**
     * Реализация метода регистрации новых пользователей на платформе
     *
     * @param request класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    @Override
    public RegistrationResponseDto registration(@Valid RegistrationRequestDto request) {
        Boolean checkUser = userRepository.existsUserByEmail(request.getEmail());

        if (checkUser) {
            throw new LoginAlreadyExistsException(REGISTRATION_MESSAGE_EXCEPTION_SERVICE);
        } else {
            User user = userMapper.toEntityUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);
            User result = userRepository.save(user);
            log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE, result);
            return userMapper.toRegistrationResponseDto(result);
        }
    }

    /**
     * Реализация метода аутентификации пользователей на платформе
     *
     * @param request класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     */
    @Override
    public AuthenticationResponseDto authentication(@Valid AuthenticationRequestDto request) {
        UserSecurity userSecurity = (UserSecurity) userSecurityService.loadUserByUsername(request.getEmail());

        if (passwordEncoder.matches(request.getPassword(), userSecurity.getPassword())) {
            log.info(AUTHENTICATION_MESSAGE_LOGGER_SERVICE, request.getEmail());
            return getGeneratingAuthenticationResponse(userSecurity);
        } else {
            log.error(AUTHENTICATION_MESSAGE_ERROR_LOGGER_SERVICE);
            throw new AuthenticationUsersException(AUTHENTICATION_USERS_MESSAGE_EXCEPTION_SERVICE);
        }
    }

    /**
     * Приватный метод для генерации ответа с личной информацией о пользователе
     *
     * @param userSecurity объект, для построения объекта аутентификации
     * @return Возвращает сгенерированный ответ с личной информацией о пользователе через DTO-класс
     */
    private AuthenticationResponseDto getGeneratingAuthenticationResponse(UserSecurity userSecurity) {
        final Long id = userSecurity.getUser().getId();
        final String firstName = userSecurity.getUser().getFirstName();
        final String lastName = userSecurity.getUser().getLastName();
        final String email = userSecurity.getUser().getEmail();
        final String password = userSecurity.getUser().getPassword();
        final String role = userSecurity.getUser().getRole().name();
        final String accessToken = tokenDetailsService.generateAccessToken(userSecurity);
        final String refreshToken = tokenDetailsService.generateRefreshToken(userSecurity);
        final LocalDateTime dateTimeCreation = LocalDateTime.now();
        final LocalDateTime dateTimeExpiresAccess = LocalDateTime.ofInstant(
                tokenDetailsService.getAccessExpiration().toInstant(), ZoneId.systemDefault());
        final LocalDateTime dateTimeExpiresRefresh = LocalDateTime.ofInstant(
                        tokenDetailsService.getRefreshExpiration().toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.MINUTES);
        Token token = tokenRepository.findTokenByUserId(id).orElseGet(() ->
                tokenService.addToken(refreshToken, userSecurity.getUser(), new Token()));
        if (token.getUser().getId().equals(id)) {
            token.setRefreshToken(refreshToken);
            token.setDateTimeCreation(dateTimeCreation);
            token.setDateTimeExpires(dateTimeExpiresRefresh);
            tokenRepository.save(token);
        }
        log.info(GET_GENERATING_AUTHENTICATION_RESPONSE_MESSAGE_LOGGER_SERVICE);
        return tokenMapper.toAuthenticationResponse(
                id,
                firstName,
                lastName,
                email,
                password,
                role,
                accessToken,
                refreshToken,
                dateTimeExpiresAccess,
                dateTimeExpiresRefresh);
    }
}
