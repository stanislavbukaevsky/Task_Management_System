package com.github.stanislavbukaevsky.taskmanagementsystem.repository;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех пользователей приложения.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link User} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Этот метод ищет пользователей по его электронной почте
     *
     * @param email электронная почта пользователя
     * @return Возвращает найденного пользователя по его электронной почте
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Этот метод проверяет, есть ли уже электронная почта в базе данных
     *
     * @param email электронная почта пользователя
     * @return Возвращает true, если пользователь уже существует в базе данных или false, если его нет
     */
    Boolean existsUserByEmail(String email);
}
