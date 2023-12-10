package com.github.stanislavbukaevsky.taskmanagementsystem.enums;

/**
 * Перечисление, для разделения приоритета задач
 */
public enum Priority {
    HIGH("Высокий"), AVERAGE("Средний"), LOW("Низкий");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
