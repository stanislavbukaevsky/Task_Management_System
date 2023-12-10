package com.github.stanislavbukaevsky.taskmanagementsystem.repository;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для работы со всеми комментариями к задачам на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Comment} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
