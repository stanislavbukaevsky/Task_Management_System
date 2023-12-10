package com.github.stanislavbukaevsky.taskmanagementsystem.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Класс-исключение для аутентификации пользователей. <br>
 * Наследуется от класса {@link AuthenticationException}
 */
public class AuthenticationUsersException extends AuthenticationException {
    public AuthenticationUsersException(String msg) {
        super(msg);
    }
}
