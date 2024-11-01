package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    @Override
    public Genre getById(int id) {
        String query = "SELECT ID, NAME FROM genre WHERE ID = ?";
        try {
            return jdbc.queryForObject(query, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Жанр с id = %d не найден", id));
        }
    }

    @Override
    public List<Genre> getAll() {
        String query = "SELECT ID, NAME FROM genre";
        return jdbc.query(query, mapper);
    }

    @Override
    public List<Genre> getFilmGenres(int filmId) {
        String query = "SELECT g.ID, g.NAME FROM film_genre fg " +
                "JOIN genre g ON g.ID = fg.GENRE_ID " +
                "WHERE fg.FILM_ID = ?";
        return jdbc.query(query, mapper, filmId);
    }

    @Override
    public void setFilmGenres(int filmId, List<Integer> genres) {
        String query = "DELETE FROM film_genre WHERE FILM_ID = ?";
        jdbc.update(query, filmId);
        query = "INSERT INTO film_genre (FILM_ID, GENRE_ID) VALUES (?, ?)";
        for (Integer genreId : genres.stream().distinct().toList()) {
            jdbc.update(query, filmId, genreId);
        }
    }
}
