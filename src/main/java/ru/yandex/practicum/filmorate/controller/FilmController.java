package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final UserStorage userStorage;

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        return filmStorage.getById(id);
    }

    /**
     * Добавление фильма
     */
    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmStorage.add(film);
    }

    /**
     * Обновление фильма
     */
    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmStorage.update(film);
    }

    /**
     * Список всех фильмов
     */
    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.getById(userId);
        filmService.addLike(film, user);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.getById(userId);
        filmService.removeLike(film, user);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        List<Integer> popularFilms = filmService.getPopularFilms(count);
        log.debug("Получен список популярных фильмов ids: {}", popularFilms);
        List<Film> res = new ArrayList<>();
        for (Integer id : popularFilms) {
            res.add(filmStorage.getById(id));
        }
        return res;
    }
}