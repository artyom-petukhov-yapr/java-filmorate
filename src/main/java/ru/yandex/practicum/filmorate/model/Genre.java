package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Жанр
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    /**
     * Идентификатор
     */
    int id;
    /**
     * Название
     */
    String name;
}
