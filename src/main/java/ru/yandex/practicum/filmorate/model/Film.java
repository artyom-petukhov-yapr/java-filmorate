package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Информация о фильме
 */
@Slf4j
@Data
public class Film {
    /**
     * Идентификатор
     */
    private int id;
    /**
     * Название
     */
    private String name;
    /**
     * Описание
     */
    private String description;
    /**
     * Дата выхода
     */
    private LocalDate releaseDate;
    /**
     * Продолжительность
     */
    private int duration;

    /**
     * Валидация данных
     * @throws ValidationException если данные некорректны
     */
    public void validate() {
        List<String> errorMessages = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            errorMessages.add("Название фильма не может быть пустым.");
        }
        if (description != null && description.length() > 200) {
            errorMessages.add("Максимальная длина описания фильма — 200 символов.");
        }
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            errorMessages.add("Дата релиза фильма не может быть раньше 28 декабря 1895 года.");
        }
        if (duration <= 0) {
            errorMessages.add("Продолжительность фильма должна быть положительным числом.");
        }

        if (!errorMessages.isEmpty()) {
            String message = String.join("\n", errorMessages);
            log.error("Ошибки валидации фильма:\n{}", message);
            throw new ValidationException(message);
        }
    }
}
