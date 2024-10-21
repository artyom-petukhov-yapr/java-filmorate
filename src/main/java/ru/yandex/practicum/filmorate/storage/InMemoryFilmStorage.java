package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Добавление фильма
     */
    @Override
    public Film add(Film film) {
        film.validate();

        int id = idCounter.incrementAndGet();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    /**
     * Обновление фильма
     */
    @Override
    public Film update(Film film) {
        getById(film.getId());
        film.validate();
        films.put(film.getId(), film);
        return film;
    }

    /**
     * Список всех фильмов
     */
    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    /**
     * @return Фильм по id
     */
    @Override
    public Film getById(Integer id) {
        Film result = films.getOrDefault(id, null);
        if (result == null) {
            throw  new NotFoundException("Фильм с id %d не найден".formatted(id));
        }
        return result;
    }
}
