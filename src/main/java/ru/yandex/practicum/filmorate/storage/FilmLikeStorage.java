package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем лайков к фильмам
 */
public interface FilmLikeStorage {

    void addLike(Film film, User user);

    void removeLike(Film film, User user);

    List<Film> getPopularFilms(int count);
}
