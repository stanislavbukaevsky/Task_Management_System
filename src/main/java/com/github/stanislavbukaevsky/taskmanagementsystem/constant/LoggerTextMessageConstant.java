package com.github.stanislavbukaevsky.taskmanagementsystem.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех логов в приложении
 */
public class LoggerTextMessageConstant {
    // Логи для методов в контроллере
    public static final String REGISTRATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для регистрации нового пользователя в контроллере. Электронная почта пользователя: {}";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для аутентификации зарегистрированного пользователя в контроллере. Электронная почта зарегистрированного пользователя: {}";
    public static final String REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для генерации нового access-токена для зарегистрированных пользователей в контроллере. Refresh-токен пользователя: {}";
    public static final String ADD_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления новой задачи в контроллере. Запрос от пользователя: {}";
    public static final String UPDATE_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения информации о задачи в контроллере. Запрос от пользователя: {}. Уникальный идентификатор задачи: {}";
    public static final String GET_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для получения информации о задачи в контроллере. Уникальный идентификатор задачи: {}";
    public static final String DELETE_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для удаления задачи в контроллере";
    public static final String UPDATE_STATUS_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения статуса задачи в контроллере. Уникальный идентификатор задачи: {}";
    public static final String UPDATE_PRIORITY_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения приоритета задачи в контроллере. Уникальный идентификатор задачи: {}";
    public static final String ASSIGN_PERFORMER_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для назначения исполнителя задачи в контроллере. Уникальный идентификатор задачи: {}. Электронная почта исполнителя: {}";
    public static final String ADD_COMMENT_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления комментария к задаче в контроллере. Запрос от пользователя: {}. Уникальный идентификатор задачи: {}";
    public static final String GET_AUTHOR_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для поиска задач конкретного автора в контроллере. Уникальный идентификатор автора: {}. Номер страницы: {}. Количество задач на странице: {}";
    public static final String GET_EXECUTOR_TASK_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для поиска задач конкретного исполнителя в контроллере. Уникальный идентификатор исполнителя: {}. Номер страницы: {}. Количество задач на странице: {}";

    // Логи для методов в сервисах
    public static final String FIND_USER_BY_EMAIL_MESSAGE_LOGGER_SERVICE = "Вызван метод поиска пользователя по его электронной почте в сервисе. Электронная почта пользователя: {}";
    public static final String REGISTRATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для регистрации нового пользователя в сервисе. Зарегистрированный пользователь: {}";
    public static final String ADD_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления в базу данных нового refresh-токена в сервисе";
    public static final String GET_GENERATING_AUTHENTICATION_RESPONSE_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации ответа с личной информацией о пользователе в сервисе";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для аутентификации зарегистрированного пользователя в сервисе. Электронная почта пользователя: {}";
    public static final String AUTHENTICATION_MESSAGE_ERROR_LOGGER_SERVICE = "Введен неправильный логин или пароль! Проверьте правильность введенных данных.";
    public static final String FIND_REFRESH_TOKEN_BY_EMAIL_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска refresh-токена по имени пользователя в сервисе. Электронная почта пользователя: {}";
    public static final String REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для генерации нового access-токена для зарегистрированных пользователей в сервисе. Refresh-токен пользователя: {}";
    public static final String ADD_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления новой задачи в сервисе. Запрос от пользователя: {}";
    public static final String UPDATE_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения информации о задачи в сервисе. Запрос от пользователя: {}. Уникальный идентификатор задачи: {}";
    public static final String GENERATING_TASK_RESPONSE_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации полной информации о задаче в сервисе";
    public static final String FIND_TASK_BY_ID_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для поиска задачи по идентификатору в сервисе. Уникальный идентификатор задачи: {}";
    public static final String GET_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для получения информации о задачи в сервисе. Уникальный идентификатор задачи: {}";
    public static final String DELETE_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для удаления задачи в сервисе";
    public static final String UPDATE_STATUS_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения статуса задачи в сервисе. Уникальный идентификатор задачи: {}";
    public static final String UPDATE_PRIORITY_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения приоритета задачи в сервисе. Уникальный идентификатор задачи: {}";
    public static final String ASSIGN_PERFORMER_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для назначения исполнителя задачи в сервисе. Уникальный идентификатор задачи: {}. Электронная почта исполнителя: {}";
    public static final String ADD_COMMENT_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления комментария к задаче в сервисе. Запрос от пользователя: {}. Уникальный идентификатор задачи: {}";
    public static final String GET_AUTHOR_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска задач конкретного автора в сервисе. Уникальный идентификатор автора: {}. Номер страницы: {}. Количество задач на странице: {}";
    public static final String GET_EXECUTOR_TASK_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска задач конкретного исполнителя в сервисе. Уникальный идентификатор исполнителя: {}. Номер страницы: {}. Количество задач на странице: {}";
    public static final String GENERATING_LIST_TASK_FULL_RESPONSE_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации списка полной информации о задачах в сервисе";

    // Логи для пакета security
    public static final String LOAD_USER_BY_USERNAME_MESSAGE_LOGGER_SECURITY = "Вызван метод для поиска пользователя по его уникальной электронной почте и помещен в security context. Электронная почта пользователя: {}";
    public static final String PASSWORD_ENCODER_ENCODE_MESSAGE_LOGGER_SERVICE = "Вызван метод шифрования пароля пользователя. Пароль пользователя: {}";
    public static final String PASSWORD_ENCODER_MATCHES_MESSAGE_LOGGER_SERVICE = "Вызван метод проверки пароля пользователя. Полученный пароль от пользователя: {}. Зашифрованный пароль, сохраненный в базе данных: {}";
}
