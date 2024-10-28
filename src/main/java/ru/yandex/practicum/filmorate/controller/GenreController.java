package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreStorage genreStorage;
    private final GenreMapper genreMapper;

    /**
     * Получить жанр по идентификатору
     */
    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable Integer id) {
        return genreMapper.genreToGenreDto(genreStorage.getById(id));
    }

    /**
     * Получить список всех жанров
     */
    @GetMapping
    public List<GenreDto> getAll() {
        return genreStorage.getAll().stream()
                .map(genreMapper::genreToGenreDto)
                .toList();
    }
}
