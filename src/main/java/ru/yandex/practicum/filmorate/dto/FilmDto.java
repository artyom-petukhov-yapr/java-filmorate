package ru.yandex.practicum.filmorate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmDto {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    MpaDto mpa;
    List<GenreDto> genres = new ArrayList<>();
}