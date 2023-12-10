package com.github.stanislavbukaevsky.taskmanagementsystem.exception;

/**
 * Класс-исключение, если произошла ошибка с поиском задачи. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
