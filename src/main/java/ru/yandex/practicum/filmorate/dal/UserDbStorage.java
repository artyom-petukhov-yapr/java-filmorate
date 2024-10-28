package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbc;
    private final UserRowMapper mapper;

    @Override
    public User add(User user) {
        user.validate();

        String query = "INSERT INTO account (NAME, LOGIN, EMAIL, BIRTHDAY) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) {
        user.validate();

        String query = "UPDATE account SET NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? WHERE ID = ?";
        int updated = jdbc.update(query, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        if (updated == 0) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден", user.getId()));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT ID, NAME, LOGIN, EMAIL, BIRTHDAY FROM account";
        return jdbc.query(query, mapper);
    }

    @Override
    public User getById(Integer id) {
        String query = "SELECT ID, NAME, LOGIN, EMAIL, BIRTHDAY FROM account WHERE ID = ?";
        try {
            return jdbc.queryForObject(query, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден", id));
        }
    }
}
