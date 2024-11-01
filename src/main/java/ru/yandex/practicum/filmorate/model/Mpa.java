package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Рейтинг
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mpa {
    /**
     * Идентификатор
     */
    int id;

    /**
     * Название
     */
    String name;
}
