package com.github.stanislavbukaevsky.taskmanagementsystem.enums;

/**
 * Перечисление, для разделения статуса задач
 */
public enum Status {
    IN_WAITING("В ожидании"), IN_PROGRESS("В процессе"), COMPLETED("Завершено");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
