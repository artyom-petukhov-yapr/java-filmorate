package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Добавление пользователя
     */
    @Override
    public User add(User user) {
        user.validate();
        if (users.values().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            throw new ValidationException(String.format("Пользователь с логином = %s уже существует", user.getLogin()));
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new ValidationException(String.format("Пользователь с email = %s уже существует", user.getEmail()));
        }
        int id = idCounter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    /**
     * Обновление пользователя
     */
    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            user.validate();
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != user.getId())) {
                throw new ValidationException(String.format("Пользователь с логином = %s уже существует", user.getLogin()));
            }
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != user.getId())) {
                throw new ValidationException(String.format("Пользователь с email = %s уже существует", user.getEmail()));
            }
            users.put(user.getId(), user);
            return user;
        }

        throw new NotFoundException(String.format("Пользователь с id = %d не найден", user.getId()));
    }

    /**
     * @return Все пользователи
     */
    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    /**
     * @return Пользователь по id
     */
    @Override
    public User getById(Integer id) {
        User result = users.getOrDefault(id, null);
        if (result == null) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден", id));
        }
        return result;
    }
}
