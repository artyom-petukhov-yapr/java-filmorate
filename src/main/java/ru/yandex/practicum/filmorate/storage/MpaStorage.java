package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем рейтингов
 */
public interface MpaStorage {
    Mpa getById(int id);

    List<Mpa> getAll();
}
