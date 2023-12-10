package com.github.stanislavbukaevsky.taskmanagementsystem.repository;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех refresh-токенов, выданных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Token} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    /**
     * Этот метод ищет refresh-токен по идентификатору пользователя
     *
     * @param id идентификатор пользователя
     * @return Возвращает найденный refresh-токен
     */
    Optional<Token> findTokenByUserId(Long id);
}
