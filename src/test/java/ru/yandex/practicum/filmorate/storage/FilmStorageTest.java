package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class})
class FilmStorageTest {
    private final FilmStorage filmStorage;

    /**
     * Добавление валидного фильма - исключения не должно быть
     */
    @Test
    void addValidFilm() {
        Film film = createValidFilm();
        assertDoesNotThrow(() -> filmStorage.add(film));
    }

    /**
     * Добавление невалидного фильма - должно быть исключение
     */
    @Test
    void addInvalidFilm() {
        Film film = createValidFilm();
        // Продолжительность фильма должна быть положительным числом, поэтому должно быть исключение при добавлении
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> filmStorage.add(film));
    }

    /**
     * Обновление фильма, которого нет в списке - должно быть исключение
     */
    @Test
    void updateNotExistFilm() {
        Film film = createValidFilm();
        film.setId(10000000);
        assertThrows(NotFoundException.class, () -> filmStorage.update(film));
    }

    /**
     * Обновление названия фильма - проверяем, что вернулся фильм с обновленными данными
     */
    @Test
    void updateFilmName() {
        Film film = createValidFilm();
        film = filmStorage.add(film);

        String newFilmName = film.getName() + " new";
        film.setName(newFilmName);

        Film updatedFilm = filmStorage.update(film);
        assertEquals(newFilmName, updatedFilm.getName());
    }

    /**
     * Обновление длительности фильма на некорректное значение - должно быть исключение
     */
    @Test
    void updateFilmDuration() {
        Film film = createValidFilm();
        film.setId(1);
        film.setDuration(-1);

        assertThrows(ValidationException.class, () -> filmStorage.update(film));
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