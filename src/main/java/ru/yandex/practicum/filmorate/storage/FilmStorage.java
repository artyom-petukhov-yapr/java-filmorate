package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем фильмов
 */
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
    List<Film> getAll();

    /**
     * Фильм по id
     */
    Film getById(Integer id);
}
