package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Film name");
        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
    }

    /**
     * Валидация корректных данных о фильме не приводит к выбросу исключения
     */
    @Test
    void validate_validFilm() {
        assertDoesNotThrow(() -> film.validate());
    }

    /**
     * Пустое название фильма приводит к выбросу исключения
     */
    @Test
    void validate_emptyName() {
        film.setName("");
        assertThrows(ValidationException.class, () -> film.validate());
    }

    /**
     * Описание фильма не может быть больше 200 символов
     */
    @Test
    void validate_longDescription() {
        film.setDescription("A".repeat(201));
        assertThrows(ValidationException.class, () -> film.validate());
    }

    /**
     * Дата релиза фильма не может быть раньше 28 декабря 1895 года
     */
    @Test
    void validate_releaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> film.validate());
    }

    /**
     * Продолжительность фильма должна быть положительным числом
     */
    @Test
    void validate_duration() {
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> film.validate());
    }
}