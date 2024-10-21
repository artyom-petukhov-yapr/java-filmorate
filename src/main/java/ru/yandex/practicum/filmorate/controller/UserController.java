package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    /**
     * Создание пользователя
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.validate();
        if (users.values().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            String message = String.format("Пользователь с логином = %s уже существует", user.getLogin());
            log.error(message);
            throw new ValidationException(message);
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            String message = String.format("Пользователь с email = %s уже существует", user.getEmail());
            log.error(message);
            throw new ValidationException(message);
        }
        int id = idCounter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    /**
     * Обновление пользователя
     */
    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            user.validate();
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != user.getId())) {
                String message = String.format("Пользователь с логином = %s уже существует", user.getLogin());
                log.error(message);
                throw new ValidationException(message);
            }
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != user.getId())) {
                String message = String.format("Пользователь с email = %s уже существует", user.getEmail());
                log.error(message);
                throw new ValidationException(message);
            }
            users.put(user.getId(), user);
            return user;
        }

        String message = String.format("Пользователь с id = %d не найден", user.getId());
        log.error(message);
        throw new NotFoundException(message);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
