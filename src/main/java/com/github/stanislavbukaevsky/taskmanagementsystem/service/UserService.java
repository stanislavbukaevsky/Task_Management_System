package com.github.stanislavbukaevsky.taskmanagementsystem.service;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;

/**
 * Сервис-интерфейс для работы со всеми пользователями зарегистрированными на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для поиска пользователей по его электронной почте
     *
     * @param email электронная почта пользователя
     * @return Возвращает найденного пользователя по его электронной почте
     */
    User findUserByEmail(String email);
}
