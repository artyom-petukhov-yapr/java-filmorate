package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем жанров
 */
public interface GenreStorage {
    Genre getById(int id);

    List<Genre> getAll();

    List<Genre> getFilmGenres(int filmId);

    void setFilmGenres(int filmId, List<Integer> genres);
}
