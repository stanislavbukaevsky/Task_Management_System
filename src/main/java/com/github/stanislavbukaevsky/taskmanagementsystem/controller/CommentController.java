package com.github.stanislavbukaevsky.taskmanagementsystem.controller;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER;

/**
 * Класс-контроллер для работы с методами комментариев к задачам
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Работа с комментариями пользователя", description = "Позволяет управлять методами по работе с комментариями пользователя")
public class CommentController {
    private final CommentService commentService;

    /**
     * Этот метод позволяет добавить новый комментарий к задаче на платформе
     *
     * @param request запрос от пользователя
     * @param idTask  уникальный идентификатор задачи
     * @return Возвращает новый, добавленный комментарий с полной информацией
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый комментарий успешно добавлен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для добавления новых комментариев к задачам",
            description = "Позволяет добавить новый комментарий к задаче для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/add-comment/{idTask}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentResponseDto> addComment(@Valid @RequestBody CommentRequestDto request,
                                                         @Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long idTask) {
        CommentResponseDto response = commentService.addComment(request, idTask);
        log.info(ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER, request, idTask);
        return ResponseEntity.ok(response);
    }
}
