package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaStorage mpaStorage;
    private final MpaMapper mpaMapper;

    /**
     * Получить жанр по идентификатору
     */
    @GetMapping("/{id}")
    public MpaDto getById(@PathVariable Integer id) {
        return mpaMapper.mpaToMpaDto(mpaStorage.getById(id));
    }

    /**
     * Получить список всех жанров
     */
    @GetMapping
    public List<MpaDto> getAll() {
        return mpaStorage.getAll().stream()
                .map(mpaMapper::mpaToMpaDto)
                .toList();
    }
}
