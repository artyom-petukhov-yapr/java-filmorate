package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        Film film = createValidFilm();
        filmController.addFilm(film);
    }

    /**
     * Изначально должен быть один фильм
     */
    @Test
    void getAllFilms() {
        assertEquals(1, filmController.getAllFilms().size());
    }

    /**
     * Изначально должен быть один фильм с идентификатором 1
     */
    @Test
    void oneFilmHasIdOne() {
        assertEquals(1, filmController.getAllFilms().iterator().next().getId());
    }

    /**
     * Добавление валидного фильма - исключения не должно быть
     */
    @Test
    void addValidFilm() {
        Film film = createValidFilm();
        assertDoesNotThrow(() -> filmController.addFilm(film));
    }

    /**
     * Добавление невалидного фильма - должно быть исключение
     */
    @Test
    void addInvalidFilm() {
        Film film = createValidFilm();
        // Продолжительность фильма должна быть положительным числом, поэтому должно быть исключение при добавлении
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    /**
     * Обновление фильма, которого нет в списке - должно быть исключение
     */
    @Test
    void updateNotExistFilm() {
        Film film = createValidFilm();
        film.setId(2);
        assertThrows(NotFoundException.class, () -> filmController.updateFilm(film));
    }

    /**
     * Обновление названия фильма - проверяем, что вернулся фильм с обновленными данными
     */
    @Test
    void updateFilmName() {
        Film film = createValidFilm();
        film.setId(1);

        String newFilmName = film.getName() + " new";
        film.setName(newFilmName);

        Film updatedFilm = filmController.updateFilm(film);
        assertEquals(newFilmName, updatedFilm.getName());
    }

    /**
     * Обновление длительности фильма на некорректное значение - должно быть исключение
     */
    @Test
    void updateFilmReleaseDate() {
        Film film = createValidFilm();
        film.setId(1);
        film.setDuration(-1);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }

    /**
     * Вспомогательный метод для создания валидного фильма
     */
    private static Film createValidFilm() {
        Film film = new Film();
        film.setName("Film name");
        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        return film;
    }
}