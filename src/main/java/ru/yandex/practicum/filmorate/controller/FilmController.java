package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmLikeStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
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
    private final FilmMapper filmMapper;

    private final FilmLikeStorage filmLikeStorage;

    private final UserStorage userStorage;

    private final GenreStorage genreStorage;
    private final GenreMapper genreMapper;

    private final String likePath = "/{id}/like/{userId}";

    /**
     * Получить фильм по идентификатору
     */
    @GetMapping("/{id}")
    public FilmDto getById(@PathVariable Integer id) {
        FilmDto film = filmMapper.mapToFilmDto(filmStorage.getById(id));
        List<Genre> genres = genreStorage.getFilmGenres(id);
        film.getGenres().addAll(genres.stream().map(genreMapper::genreToGenreDto).toList());
        return film;
    }

    /**
     * Добавить фильм
     */
    @PostMapping
    public FilmDto addFilm(@RequestBody FilmDto film) {
        return filmMapper.mapToFilmDto(filmStorage.add(filmMapper.mapToFilm(film)));
    }

    /**
     * Обновить данные о фильме
     */
    @PutMapping
    public FilmDto updateFilm(@RequestBody FilmDto film) {
        return filmMapper.mapToFilmDto(filmStorage.update(filmMapper.mapToFilm(film)));
    }

    /**
     * Получить список всех фильмов
     */
    @GetMapping
    public Collection<FilmDto> getAllFilms() {
        return filmStorage.getAll().stream().map(filmMapper::mapToFilmDto).toList();
    }

    /**
     * Добавить лайк к фильму
     */
    @PutMapping(likePath)
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.getById(userId);
        filmLikeStorage.addLike(film, user);
    }

    /**
     * Удалить лайк к фильму
     */
    @DeleteMapping(likePath)
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.getById(userId);
        filmLikeStorage.removeLike(film, user);
    }

    /**
     * Получить список популярных фильмов
     */
    @GetMapping("/popular")
    public List<FilmDto> getPopularFilms(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        List<Film> popularFilms = filmLikeStorage.getPopularFilms(count);
        log.debug("Получен список популярных фильмов ids: {}", popularFilms);
        List<FilmDto> res = new ArrayList<>();
        for (Film film : popularFilms) {
            res.add(filmMapper.mapToFilmDto(film));
        }
        return res;
    }
}