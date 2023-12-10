package com.github.stanislavbukaevsky.taskmanagementsystem.service.impl;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskFullResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Task;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.TaskNotFoundException;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.UserByIdNotFoundException;
import com.github.stanislavbukaevsky.taskmanagementsystem.mapper.TaskMapper;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.CommentRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.TaskRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.UserRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurity;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.TaskService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для работы с методами всех задач опубликованных на платформе.
 * Реализует интерфейс {@link TaskService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final UserSecurity userSecurity;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * Реализация метода для добавления новых задач на платформу
     *
     * @param request  запрос от пользователя
     * @param status   статус задачи
     * @param priority приоритет задачи
     * @return Возвращает новую, добавленную задачу
     */
    @Override
    public TaskResponseDto addTask(@Valid TaskRequestDto request, Status status, Priority priority) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        LocalDateTime dateTime = LocalDateTime.now();

        Task task = taskMapper.toEntityTask(request);
        task.setDateTime(dateTime);
        task.setStatus(status);
        task.setPriority(priority);
        task.setAuthor(user);
        Task result = taskRepository.save(task);

        log.info(ADD_TASK_MESSAGE_LOGGER_SERVICE, request);
        return generatingTaskResponse(result, user);
    }

    /**
     * Реализация метода для изменения информации о задачи на платформе
     *
     * @param request запрос от пользователя
     * @param id      уникальный идентификатор задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @Override
    public TaskResponseDto updateTask(@Valid TaskRequestDto request, @Positive Long id) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        Task task = findTaskById(id);

        if (task.getAuthor().getEmail().equals(userSecurity.getUsername())) {
            task.setHeading(request.getHeading());
            task.setDescription(request.getDescription());
            Task result = taskRepository.save(task);
            log.info(UPDATE_TASK_MESSAGE_LOGGER_SERVICE, request, id);
            return generatingTaskResponse(result, user);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для получения информации о задачи на платформе
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает найденную задачу с полной информацией
     */
    @Override
    public TaskFullResponseDto getTask(@Positive Long id) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        Task task = findTaskById(id);
        List<Comment> comments = task.getComments();

        if (task.getAuthor().getEmail().equals(userSecurity.getUsername())) {
            log.info(GET_TASK_MESSAGE_LOGGER_SERVICE, id);
            return generatingTaskFullResponse(task, user, comments);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для удаления задачи с платформы
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает информационную строку об успешности операции
     */
    @Override
    public String deleteTask(@Positive Long id) {
        Task task = findTaskById(id);
        List<Comment> comments = task.getComments();

        if (task.getAuthor().getEmail().equals(userSecurity.getUsername())) {
            comments.stream().forEach(comment -> commentRepository.deleteById(comment.getId()));
            taskRepository.delete(task);
            log.info(DELETE_TASK_MESSAGE_LOGGER_SERVICE);
            return "Задача успешно удалена!";
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для изменения статуса задачи на платформе
     *
     * @param id     уникальный идентификатор задачи
     * @param status статус задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @Override
    public TaskResponseDto updateStatusTask(@Positive Long id, Status status) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        Task task = findTaskById(id);

        if (task.getExecutor().getEmail().equals(user.getEmail())) {
            task.setStatus(status);
            Task result = taskRepository.save(task);
            log.info(UPDATE_STATUS_TASK_MESSAGE_LOGGER_SERVICE, id);
            return generatingTaskResponse(result, user);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для изменения приоритета задачи на платформе
     *
     * @param id       уникальный идентификатор задачи
     * @param priority приоритет задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @Override
    public TaskResponseDto updatePriorityTask(@Positive Long id, Priority priority) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        Task task = findTaskById(id);

        if (task.getAuthor().getEmail().equals(userSecurity.getUsername())) {
            task.setPriority(priority);
            Task result = taskRepository.save(task);
            log.info(UPDATE_PRIORITY_TASK_MESSAGE_LOGGER_SERVICE, id);
            return generatingTaskResponse(result, user);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для назначения исполнителя задачи на платформе
     *
     * @param idTask уникальный идентификатор задачи
     * @param email  электронная почта исполнителя
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    @Override
    public TaskResponseDto assignPerformerTask(@Positive Long idTask, String email) {
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        User executor = userService.findUserByEmail(email);
        Task task = findTaskById(idTask);

        if (task.getAuthor().getEmail().equals(userSecurity.getUsername())) {
            task.setExecutor(executor);
            Task result = taskRepository.save(task);
            log.info(ASSIGN_PERFORMER_TASK_MESSAGE_LOGGER_SERVICE, idTask, email);
            return generatingTaskResponse(result, user);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
    }

    /**
     * Реализация метода для поиска задач конкретного автора.
     * Также, метод поддерживает пагинацию и сортировку по времени создания задачи
     *
     * @param id   уникальный идентификатор автора задачи
     * @param page номер страницы
     * @param size количество задач на странице
     * @return Возвращает список найденных задач конкретного автора
     */
    @Override
    public List<TaskFullResponseDto> getAuthorTask(@Positive Long id, @Positive int page, @Positive int size) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserByIdNotFoundException(USER_BY_ID_NOT_FOUND_EXCEPTION));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<Task> tasks = taskRepository.findTasksByAuthorId(user.getId(), pageable);

        log.info(GET_AUTHOR_TASK_MESSAGE_LOGGER_SERVICE, id, page, size);
        return generatingListTaskFullResponse(tasks);
    }

    /**
     * Реализация метода для поиска задач конкретного исполнителя.
     * Также, метод поддерживает пагинацию и сортировку по времени изменения задачи
     *
     * @param id   уникальный идентификатор исполнителя задачи
     * @param page номер страницы
     * @param size количество задач на странице
     * @return Возвращает список найденных задач конкретного исполнителя
     */
    @Override
    public List<TaskFullResponseDto> getExecutorTask(@Positive Long id, @Positive int page, @Positive int size) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserByIdNotFoundException(USER_BY_ID_NOT_FOUND_EXCEPTION));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<Task> tasks = taskRepository.findTasksByExecutorId(user.getId(), pageable);

        log.info(GET_EXECUTOR_TASK_MESSAGE_LOGGER_SERVICE, id, page, size);
        return generatingListTaskFullResponse(tasks);
    }

    /**
     * Приватный метод для генерации ответа пользователю с информацией о задаче
     *
     * @param task сущность задачи
     * @param user сущность пользователя
     * @return Возвращает полную информацию о задаче
     */
    private TaskResponseDto generatingTaskResponse(Task task, User user) {
        TaskResponseDto response = taskMapper.toTaskResponse(task);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        log.info(GENERATING_TASK_RESPONSE_MESSAGE_LOGGER_SERVICE);
        return response;
    }

    /**
     * Приватный метод для генерации ответа пользователю с информацией о задаче
     *
     * @param task сущность задачи
     * @param user сущность пользователя
     * @return Возвращает полную информацию о задаче
     */
    private TaskFullResponseDto generatingTaskFullResponse(Task task, User user, List<Comment> comments) {
        TaskFullResponseDto response = taskMapper.toTaskFullResponse(task);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setComments(comments);
        log.info(GENERATING_TASK_RESPONSE_MESSAGE_LOGGER_SERVICE);
        return response;
    }

    /**
     * Приватный метод для генерации списка ответов пользователю с информацией о задачах
     *
     * @param tasks список задач
     * @return Возвращает полную информацию о задачах
     */
    private List<TaskFullResponseDto> generatingListTaskFullResponse(List<Task> tasks) {
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION_2);
        }

        List<TaskFullResponseDto> responses = new ArrayList<>();
        for (Task task : tasks) {
            TaskFullResponseDto response = generatingTaskFullResponse(task, task.getAuthor(), task.getComments());
            responses.add(response);
        }
        log.info(GENERATING_LIST_TASK_FULL_RESPONSE_MESSAGE_LOGGER_SERVICE);
        return responses;
    }

    /**
     * Приватный метод для поиска задачи по идентификатору
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает найденную задачу
     */
    private Task findTaskById(Long id) {
        log.info(FIND_TASK_BY_ID_MESSAGE_LOGGER_SERVICE, id);
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION));
    }
}
