package com.github.stanislavbukaevsky.taskmanagementsystem.service.impl;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import com.github.stanislavbukaevsky.taskmanagementsystem.repository.UserRepository;
import com.github.stanislavbukaevsky.taskmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.ExceptionTextMessageConstant.USER_NOT_FOUND_EXCEPTION;
import static com.github.stanislavbukaevsky.taskmanagementsystem.constant.LoggerTextMessageConstant.FIND_USER_BY_EMAIL_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для всех пользователей зарегистрированных на платформе.
 * Реализует интерфейс {@link UserService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Реализация метода для поиска пользователей по его электронной почте
     *
     * @param email электронная почта пользователя
     * @return Возвращает найденного пользователя по его электронной почте
     */
    @Override
    public User findUserByEmail(String email) {
        log.info(FIND_USER_BY_EMAIL_MESSAGE_LOGGER_SERVICE, email);
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION));
    }
}
