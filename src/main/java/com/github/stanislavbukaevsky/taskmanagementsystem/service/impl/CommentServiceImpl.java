package com.github.stanislavbukaevsky.taskmanagementsystem.service.impl;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Task;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.exception.TaskNotFoundException;
import com.github.stanislavbukaevsky.taskmanagementsystem.mapper.CommentMapper;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.CommentRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.TaskRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.security.UserSecurity;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.CommentService;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.TASK_NOT_FOUND_EXCEPTION;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.ADD_COMMENT_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для работы с методами всех комментариев к задачам опубликованными на платформе.
 * Реализует интерфейс {@link CommentService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final UserSecurity userSecurity;
    private final UserService userService;

    /**
     * Реализация метода для добавления новых комментариев к задачам на платформу
     *
     * @param request запрос от пользователя
     * @param idTask  уникальный идентификатор задачи
     * @return Возвращает новый, добавленный комментарий с полной информацией
     */
    @Override
    public CommentResponseDto addComment(@Valid CommentRequestDto request, @Positive Long idTask) {
        LocalDateTime dateTime = LocalDateTime.now();
        User user = userService.findUserByEmail(userSecurity.getUser().getEmail());
        Task task = taskRepository.findById(idTask).orElseThrow(() ->
                new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION));
        Comment comment = commentMapper.toEntityComment(request);
        comment.setDateTime(dateTime);
        comment.setTask(task);
        comment.setUser(user);
        Comment result = commentRepository.save(comment);

        CommentResponseDto response = commentMapper.toCommentResponse(result);
        response.setHeadingTask(task.getHeading());
        response.setDescriptionTask(task.getDescription());
        response.setStatusTask(task.getStatus().getDescription());
        response.setPriorityTask(task.getPriority().getDescription());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        log.info(ADD_COMMENT_MESSAGE_LOGGER_SERVICE, request, idTask);
        return response;
    }
}
