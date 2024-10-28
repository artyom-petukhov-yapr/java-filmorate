package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;
    private final GenreStorage genreStorage;

    @Override
    public Film add(Film film) {
        film.validate();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO FILM (NAME, RELEASE_DATE, DURATION, DESCRIPTION, MPA_ID) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, film.getName());
            stmt.setDate(2, java.sql.Date.valueOf(film.getReleaseDate()));
            stmt.setInt(3, film.getDuration());
            stmt.setString(4, film.getDescription());
            if (film.getMpa() != null && film.getMpa() > 0) {
                stmt.setInt(5, film.getMpa());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            return stmt;
        }, keyHolder);
        film.setId((Integer) keyHolder.getKey());
        genreStorage.setFilmGenres(film.getId(), film.getGenres());
        return film;
    }

    @Override
    public Film update(Film film) {
        film.validate();

        String query = "UPDATE FILM SET NAME = ?, RELEASE_DATE = ?, DURATION = ?, DESCRIPTION = ?, MPA_ID = ? WHERE ID = ?";
        int updated = jdbc.update(query, film.getName(), film.getReleaseDate(), film.getDuration(), film.getDescription(), film.getMpa(), film.getId());
        if (updated == 0) {
            throw new NotFoundException(String.format("Фильм с id = %d не найден", film.getId()));
        }
        genreStorage.setFilmGenres(film.getId(), film.getGenres());
        return film;
    }

    @Override
    public List<Film> getAll() {
        String query = "SELECT f.ID, f.NAME, f.RELEASE_DATE, f.DURATION, f.DESCRIPTION, f.MPA_ID, m.NAME as MPA_NAME " +
                "FROM FILM f " +
                "LEFT JOIN MPA m ON m.ID = f.MPA_ID";
        return jdbc.query(query, mapper);
    }

    @Override
    public Film getById(Integer id) {
        String query = "SELECT f.ID, f.NAME, f.RELEASE_DATE, f.DURATION, f.DESCRIPTION, f.MPA_ID, m.NAME as MPA_NAME " +
                "FROM FILM f " +
                "LEFT JOIN MPA m ON m.ID = f.MPA_ID " +
                "WHERE f.ID = ?";
        return jdbc.queryForObject(query, mapper, id);
    }
}
