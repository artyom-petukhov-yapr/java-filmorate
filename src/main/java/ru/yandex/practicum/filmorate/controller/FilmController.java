package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Добавление фильма
     */
    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        film.validate();

        int id = idCounter.incrementAndGet();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    /**
     * Обновление фильма
     */
    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            film.validate();
            films.put(film.getId(), film);
            return film;
        }
        String message = String.format("Фильм с id %d не найден", film.getId());
        log.error(message);
        throw new NotFoundException(message);
    }

    /**
     * Список всех фильмов
     */
    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}