package com.github.stanislavbukaevsky.taskmanagementsystem.mapper;

import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskFullResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskRequestDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.dto.TaskResponseDto;
import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Task;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Priority;
import com.github.stanislavbukaevsky.taskmanagementsystem.enums.Status;
import org.mapstruct.Mapper;

/**
 * Маппер-интерфейс, который преобразует информацию о задачах в DTO
 */
@Mapper
public interface TaskMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о задачи
     *
     * @param request DTO запроса с информацией
     * @return Возвращает сформированную модель с информацией о задачи
     */
    Task toEntityTask(TaskRequestDto request);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о задачи
     *
     * @param task сущность задачи
     * @return Возвращает сформированную DTO с ответом пользователю о задачи
     */
    TaskResponseDto toTaskResponse(Task task);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о задачи
     *
     * @param task сущность задачи
     * @return Возвращает сформированную DTO с ответом пользователю о задачи
     */
    TaskFullResponseDto toTaskFullResponse(Task task);

    /**
     * Этот метод формирует описание перечисления
     *
     * @param status перечисление о статусе задачи
     * @return Возвращает подробное описание статуса задачи
     */
    default String toTaskStatusDescription(Status status) {
        return status.getDescription();
    }

    /**
     * Этот метод формирует описание перечисления
     *
     * @param priority перечисление о приоритете задачи
     * @return Возвращает подробное описание приоритета задачи
     */
    default String toTaskPriorityDescription(Priority priority) {
        return priority.getDescription();
    }
}
