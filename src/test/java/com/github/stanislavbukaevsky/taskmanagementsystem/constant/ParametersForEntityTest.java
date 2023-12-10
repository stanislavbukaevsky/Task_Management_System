package com.github.stanislavbukaevsky.taskmanagementsystem.constant;

import java.time.LocalDateTime;

public class ParametersForEntityTest {
    public static final Long ID_COMMENT = 1L;
    public static final String TEXT_COMMENT = "Текст комментария";
    public static final LocalDateTime DATE_TIME = LocalDateTime.now();
    public static final Long ID_COMMENT_2 = 10L;
    public static final String TEXT_COMMENT_2 = "Текст комментария 2";
    public static final LocalDateTime DATE_TIME_2 = LocalDateTime.now();

    public static final Long ID_TASK = 4L;
    public static final String HEADING_TASK = "Заголовок задачи";
    public static final String DESCRIPTION_TASK = "Описание задачи";

    public static final String ACCESS_TOKEN = "eyJhbGUJ9.eyJpc3MiOjM4fQ.xeQ0ZEeQy572MLCZ_Tjy2g-ufQ-WIPk37QCA";
    public static final String REFRESH_TOKEN = "eyJhbGUzUxMiJ9.eyJpc3MiOicyMTI1NjM4fQ.xeQ0ZEeQy572MLCZ_Tjy2g-ufKNdQ-WIPk37QCA";
    public static final LocalDateTime EXPIRES_ACCESS = LocalDateTime.now();
    public static final LocalDateTime EXPIRES_REFRESH = LocalDateTime.now();

    public static final Long ID_USER = 8L;
    public static final String FIRST_NAME_USER = "Иван";
    public static final String LAST_NAME_USER = "Иванов";
    public static final String EMAIL_USER = "ivan@mail.ru";
    public static final String PASSWORD_USER = "123";
}
