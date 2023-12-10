package com.github.stanislavbukaevsky.taskmanagementsystem.service;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.CommentResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * Сервис-интерфейс для работы со всеми комментариями к задачам на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface CommentService {
    /**
     * Сигнатура метода для добавления новых комментариев к задачам на платформу
     *
     * @param request запрос от пользователя
     * @param idTask  уникальный идентификатор задачи
     * @return Возвращает новый, добавленный комментарий с полной информацией
     */
    CommentResponseDto addComment(@Valid CommentRequestDto request, @Positive Long idTask);
}
