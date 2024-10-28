package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmLikeStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmLikeDbStorage implements FilmLikeStorage {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;

    @Override
    public void addLike(Film film, User user) {
        String query = "INSERT INTO film_like (FILM_ID, ACCOUNT_ID) VALUES (?, ?)";
        jdbc.update(query, film.getId(), user.getId());
    }

    @Override
    public void removeLike(Film film, User user) {
        String query = "DELETE FROM film_like WHERE FILM_ID = ? AND ACCOUNT_ID = ?";
        jdbc.update(query, film.getId(), user.getId());
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String query = "SELECT f.ID, f.NAME, f.RELEASE_DATE, f.DURATION, f.DESCRIPTION, f.MPA_ID, mpa.NAME as MPA_NAME " +
                "FROM film_like fl " +
                "JOIN film f ON f.ID = fl.FILM_ID " +
                "JOIN mpa on f.MPA_ID = mpa.ID " +
                "GROUP BY f.ID " +
                "ORDER BY COUNT(f.ID) DESC " +
                "LIMIT ?";
        return jdbc.query(query, mapper, count);
    }
}
