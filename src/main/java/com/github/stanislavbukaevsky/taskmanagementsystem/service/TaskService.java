package com.github.stanislavbukaevsky.taskmanagementsystem.service;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskFullResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * Сервис-интерфейс для работы со всеми задачами на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface TaskService {
    /**
     * Сигнатура метода для добавления новых задач на платформу
     *
     * @param request  запрос от пользователя
     * @param status   статус задачи
     * @param priority приоритет задачи
     * @return Возвращает новую, добавленную задачу
     */
    TaskResponseDto addTask(@Valid TaskRequestDto request, Status status, Priority priority);

    /**
     * Сигнатура метода для изменения информации о задачи на платформе
     *
     * @param request запрос от пользователя
     * @param id      уникальный идентификатор задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    TaskResponseDto updateTask(@Valid TaskRequestDto request, @Positive Long id);

    /**
     * Сигнатура метода для получения информации о задачи на платформе
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает найденную задачу с полной информацией
     */
    TaskFullResponseDto getTask(@Positive Long id);

    /**
     * Сигнатура метода для удаления задачи с платформы
     *
     * @param id уникальный идентификатор задачи
     * @return Возвращает информационную строку об успешности операции
     */
    String deleteTask(@Positive Long id);

    /**
     * Сигнатура метода для изменения статуса задачи на платформе
     *
     * @param id     уникальный идентификатор задачи
     * @param status статус задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    TaskResponseDto updateStatusTask(@Positive Long id, Status status);

    /**
     * Сигнатура метода для изменения приоритета задачи на платформе
     *
     * @param id       уникальный идентификатор задачи
     * @param priority приоритет задачи
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    TaskResponseDto updatePriorityTask(@Positive Long id, Priority priority);

    /**
     * Сигнатура метода для назначения исполнителя задачи на платформе
     *
     * @param idTask уникальный идентификатор задачи
     * @param email  электронная почта исполнителя
     * @return Возвращает измененную задачу с полной информацией о ней
     */
    TaskResponseDto assignPerformerTask(@Positive Long idTask, String email);

    /**
     * Сигнатура метода для поиска задач конкретного автора.
     * Также, метод поддерживает пагинацию и сортировку по времени создания задачи
     *
     * @param id   уникальный идентификатор автора задачи
     * @param page номер страницы
     * @param size Количество задач на странице
     * @return Возвращает список найденных задач конкретного автора
     */
    List<TaskFullResponseDto> getAuthorTask(@Positive Long id, @Positive int page, @Positive int size);

    /**
     * Сигнатура метода для поиска задач конкретного исполнителя.
     * Также, метод поддерживает пагинацию и сортировку по времени изменения задачи
     *
     * @param id   уникальный идентификатор исполнителя задачи
     * @param page номер страницы
     * @param size количество задач на странице
     * @return Возвращает список найденных задач конкретного исполнителя
     */
    List<TaskFullResponseDto> getExecutorTask(@Positive Long id, @Positive int page, @Positive int size);
}
