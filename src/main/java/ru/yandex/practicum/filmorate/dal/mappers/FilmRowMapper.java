package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film result = new Film();
        result.setId(resultSet.getInt("ID"));
        result.setName(resultSet.getString("NAME"));
        result.setReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate());
        result.setDuration(resultSet.getInt("DURATION"));
        result.setDescription(resultSet.getString("DESCRIPTION"));
        result.setMpa(resultSet.getInt("MPA_ID"));
        return result;
    }
}
