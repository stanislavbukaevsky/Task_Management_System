package com.github.stanislavbukaevsky.taskmanagementsystem.repository;

import com.github.stanislavbukaevsky.taskmanagementsystem.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс-репозиторий для работы со всеми задачами на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Task} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * Этот метод ищет задачи конкретного автора в выбранном порядке
     *
     * @param id       уникальный идентификатор автора задачи
     * @param pageable объект пагинации
     * @return Возвращает список найденных задач конкретного автора
     */
    List<Task> findTasksByAuthorId(Long id, Pageable pageable);

    /**
     * Этот метод ищет задачи конкретного исполнителя в выбранном порядке
     *
     * @param id       уникальный идентификатор исполнителя задачи
     * @param pageable объект пагинации
     * @return Возвращает список найденных задач конкретного исполнителя
     */
    List<Task> findTasksByExecutorId(Long id, Pageable pageable);
}
