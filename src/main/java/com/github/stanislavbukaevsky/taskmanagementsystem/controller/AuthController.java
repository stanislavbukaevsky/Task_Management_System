package com.github.stanislavbukaevsky.taskmanagementsystem.controller;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AuthenticationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.REGISTRATION_MESSAGE_LOGGER_CONTROLLER;

/**
 * Класс-контроллер для работы с регистрацией и аутентификацией пользователей на платформе
 */
@Slf4j
@RestController
@RequestMapping("/inputs")
@RequiredArgsConstructor
@Tag(name = "Работа с регистрацией и аутентификацией",
        description = "Позволяет управлять методами по работе с регистрацией и аутентификацией пользователей на платформе")
public class AuthController {
    private final AuthService authService;

    /**
     * Этот метод позволяет зарегистрировать нового пользователя на платформе
     *
     * @param request класс-DTO для регистрации пользователя на платформе
     * @return Возвращает зарегистрированного пользователя и всю информацию о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь зарегистрирован (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RegistrationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод регистрации пользователей на платформе",
            description = "Позволяет зарегистрироваться новому пользователю на платформе")
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponseDto> registration(@Valid @RequestBody RegistrationRequestDto request) {
        RegistrationResponseDto response = authService.registration(request);
        log.info(REGISTRATION_MESSAGE_LOGGER_CONTROLLER, request.getEmail());
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет аутентифицироваться (войти в приложение) пользователю на платформе
     *
     * @param request класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает аутентифицированного пользователя, если такой существует
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод аутентификации пользователей на платформе",
            description = "Позволяет аутентифицироваться зарегистрированному пользователю на платформе")
    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> authentication(@Valid @RequestBody AuthenticationRequestDto request) {
        AuthenticationResponseDto response = authService.authentication(request);
        log.info(AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER, request.getEmail());
        return ResponseEntity.ok(response);
    }
}
