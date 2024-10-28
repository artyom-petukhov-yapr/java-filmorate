package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем пользователей
 */
public interface UserStorage {

    /**
     * Добавление пользователя
     */
    User add(User user);

    /**
     * Обновление пользователя
     */
    User update(User user);

    /**
     * Все пользователи
     */
    List<User> getAll();

    /**
     * Пользователь по id
     */
    User getById(Integer id);
}
