package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    /**
     * Добавление фильма
     */
    Film add(Film film);

    /**
     * Обновление фильма
     */
    Film update(Film film);

    /**
     * Список всех фильмов
     */
    Collection<Film> getAll();

    /**
     * Фильм по id
     */
    Film getById(Integer id);
}
