package com.github.stanislavbukaevsky.taskmanagementsystem.controller;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.AccessTokenResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.TokenService;
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

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_CONTROLLER;

/**
 * Класс-контроллер для работы с методами получения нового access-токена
 */
@Slf4j
@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
@Tag(name = "Работа с токенами пользователя", description = "Позволяет управлять методами по работе с токенами пользователя")
public class TokenController {
    private final TokenService tokenService;

    /**
     * Этот метод позволяет получить новый access-токен для зарегистрированного пользователя на платформе
     *
     * @param request класс-DTO с refresh-токеном, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый access-токен успешно выдан (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccessTokenResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для получения новго access-токена", description = "Позволяет сгенерировать новый access-токен для зарегистрированных пользователей на платформе")
    @PostMapping(value = "/access-new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessTokenResponseDto> replaceAccessToken(@Valid @RequestBody AccessTokenRequestDto request) {
        AccessTokenResponseDto response = tokenService.replaceAccessToken(request);
        log.info(REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_CONTROLLER, request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
