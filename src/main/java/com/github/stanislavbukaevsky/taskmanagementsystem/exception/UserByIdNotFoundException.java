package com.github.stanislavbukaevsky.taskmanagementsystem.exception;

/**
 * Класс-исключение, если произошла ошибка с поиском пользователя. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class UserByIdNotFoundException extends RuntimeException {
    public UserByIdNotFoundException(String message) {
        super(message);
    }
}
