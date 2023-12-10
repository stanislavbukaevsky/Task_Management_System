package com.github.stanislavbukaevsky.taskmanagementsystem.controller;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskFullResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.TaskService;
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

import java.util.List;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с методами опубликованных задач
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Работа с задачами пользователя", description = "Позволяет управлять методами по работе с задачами пользователя")
public class TaskController {
    private final TaskService taskService;

    /**
     * Этот метод позволяет добавить новую задачу на платформу
     *
     * @param request  запрос от пользователя
     * @param status   статус задачи
     * @param priority приоритет задачи
     * @return Возвращает новую, добавленную задачу
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новая задача успешна создана (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для создания новой задачи",
            description = "Позволяет создать новую задачу для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/add-task", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> addTask(@Valid @RequestBody TaskRequestDto request,
                                                   @Parameter(description = "Статус задачи") @RequestParam Status status,
                                                   @Parameter(description = "Приоритет задачи") @RequestParam Priority priority) {
        TaskResponseDto response = taskService.addTask(request, status, priority);
        log.info(ADD_TASK_MESSAGE_LOGGER_CONTROLLER, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет изменить информацию о задачи на платформе
     *
     * @param request запрос от пользователя
     * @param id      уникальный идентификатор задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешна изменена (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для изменения информации о задачи",
            description = "Позволяет изменить информацию о задачи для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/update-task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> updateTask(@Valid @RequestBody TaskRequestDto request,
                                                      @Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long id) {
        TaskResponseDto response = taskService.updateTask(request, id);
        log.info(UPDATE_TASK_MESSAGE_LOGGER_CONTROLLER, request, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет получить информацию о задачи на платформе
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает найденную задачу с полной информацией
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешна получена (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = TaskFullResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для получения информации о задачи",
            description = "Позволяет получить информацию о задачи для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/get-task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskFullResponseDto> getTask(@Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long id) {
        TaskFullResponseDto response = taskService.getTask(id);
        log.info(GET_TASK_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет удалить задачу с платформы
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает информационную строку об успешности операции
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешна удалена (OK)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для удаления задачи с платформы",
            description = "Позволяет удалить задачу для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<String> deleteTask(@Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long id) {
        String response = taskService.deleteTask(id);
        log.info(DELETE_TASK_MESSAGE_LOGGER_CONTROLLER);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет изменить статус задачи на платформе
     *
     * @param id     уникальный идентификатор задачи
     * @param status статус задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно изменен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для изменения статуса задачи",
            description = "Позволяет изменить статус задачи для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/update-status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> updateStatusTask(@Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long id,
                                                            @Parameter(description = "Статус задачи") @RequestParam Status status) {
        TaskResponseDto response = taskService.updateStatusTask(id, status);
        log.info(UPDATE_STATUS_TASK_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет изменить приоритет задачи на платформе
     *
     * @param id       уникальный идентификатор задачи
     * @param priority приоритет задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приоритет задачи успешно изменен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для изменения приоритета задачи",
            description = "Позволяет изменить приоритет задачи для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/update-priority/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> updatePriorityTask(@Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long id,
                                                              @Parameter(description = "Приоритет задачи") @RequestParam Priority priority) {
        TaskResponseDto response = taskService.updatePriorityTask(id, priority);
        log.info(UPDATE_PRIORITY_TASK_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет назначить исполнителя задачи на платформе
     *
     * @param idTask уникальный идентификатор задачи
     * @param email  электронная почта исполнителя
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Исполнитель задачи успешно назначен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для назначения исполнителя задачи",
            description = "Позволяет назначить исполнителя задачи для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/assign-performer/{idTask}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> assignPerformerTask(@Parameter(description = "Уникальный идентификатор задачи") @PathVariable @Positive Long idTask,
                                                               @Parameter(description = "Электронная почта исполнителя") @RequestParam String email) {
        TaskResponseDto response = taskService.assignPerformerTask(idTask, email);
        log.info(ASSIGN_PERFORMER_TASK_MESSAGE_LOGGER_CONTROLLER, idTask, email);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет получить список задач конкретного автора
     *
     * @param id   уникальный идентификатор автора задачи
     * @param page номер страницы
     * @param size количество задач на странице
     * @return Возвращает список найденных задач конкретного автора
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = TaskFullResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для получения списка задач конкретного автора",
            description = "Позволяет получить список задач конкретного автора для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/author-task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskFullResponseDto>> getAuthorTask(@Parameter(description = "Уникальный идентификатор автора задачи") @PathVariable @Positive Long id,
                                                                   @Parameter(description = "Номер страницы") @RequestParam(required = false, defaultValue = "0") @Positive int page,
                                                                   @Parameter(description = "Количество задач на странице") @RequestParam(required = false, defaultValue = "5") @Positive int size) {
        List<TaskFullResponseDto> responses = taskService.getAuthorTask(id, page, size);
        log.info(GET_AUTHOR_TASK_MESSAGE_LOGGER_CONTROLLER, id, page, size);
        return ResponseEntity.ok(responses);
    }

    /**
     * Этот метод позволяет получить список задач конкретного исполнителя
     *
     * @param id   уникальный идентификатор исполнителя задачи
     * @param page номер страницы
     * @param size количество задач на странице
     * @return Возвращает список найденных задач конкретного исполнителя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = TaskFullResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для получения списка задач конкретного исполнителя",
            description = "Позволяет получить список задач конкретного исполнителя для зарегистрированных пользователей на платформе")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/executor-task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskFullResponseDto>> getExecutorTask(@Parameter(description = "Уникальный идентификатор исполнителя задачи") @PathVariable @Positive Long id,
                                                                     @Parameter(description = "Номер страницы") @RequestParam(required = false, defaultValue = "0") @Positive int page,
                                                                     @Parameter(description = "Количество задач на странице") @RequestParam(required = false, defaultValue = "5") @Positive int size) {
        List<TaskFullResponseDto> responses = taskService.getExecutorTask(id, page, size);
        log.info(GET_EXECUTOR_TASK_MESSAGE_LOGGER_CONTROLLER, id, page, size);
        return ResponseEntity.ok(responses);
    }
}
