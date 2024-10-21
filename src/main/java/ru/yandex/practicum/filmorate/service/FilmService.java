package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для хранения лайков для фильмов
 *
 * @Note: на вход методы принимают объекты типа Film и User. Это "гарантирует" то, что вызывающая сторона
 * смогла получить объекты из хранилища и на вход не придут, например, невалидные ИД
 */
@Service
@Slf4j
public class FilmService {
    /**
     * Для каждого фильма хранится список пользователей, которые поставили лайк
     */
    private final HashMap<Integer, HashSet<Integer>> filmLikes = new HashMap<>();
    /**
     * Для каждого пользователя хранится список фильмов, которые он поставил лайк
     */
    private final HashMap<Integer, HashSet<Integer>> userLikes = new HashMap<>();

    public void addLike(Film film, User user) {
        userLikes.computeIfAbsent(user.getId(), k -> new HashSet<>()).add(film.getId());
        filmLikes.computeIfAbsent(film.getId(), k -> new HashSet<>()).add(user.getId());
        log.debug("Пользователь с id = {} поставил лайк на фильм с id = {}", user.getId(), film.getId());
    }

    public void removeLike(Film film, User user) {
        userLikes.computeIfAbsent(user.getId(), k -> new HashSet<>()).remove(film.getId());
        filmLikes.computeIfAbsent(film.getId(), k -> new HashSet<>()).remove(user.getId());
        log.debug("Пользователь с id = {} удалил лайк на фильм с id = {}", user.getId(), film.getId());
    }

    public List<Integer> getPopularFilms(int count) {
        return filmLikes.entrySet().stream()
                // сортируем фильмы по количеству лайков (по убыванию)
                .sorted((o1, o2) -> Integer.compare(o2.getValue().size(), o1.getValue().size()))
                // оставляем только первые count элементов
                .limit(count)
                // извлекаем ключи
                .map(Map.Entry::getKey)
                // преобразуем в коллекцию
                .collect(Collectors.toList());
    }
}
