package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

@Component
@RequiredArgsConstructor
public final class FilmMapper {
    private final MpaStorage mpaStorage;
    private final MpaMapper mpaMapper;

    private final GenreStorage genreStorage;
    private final GenreMapper genreMapper;

    public FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        if (film.getMpa() != null) {
            dto.setMpa(mpaMapper.mpaToMpaDto(mpaStorage.getById(film.getMpa())));
        }
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre -> dto.getGenres().add(genreMapper.genreToGenreDto(genreStorage.getById(genre))));
        }
        return dto;
    }

    public Film mapToFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        film.setReleaseDate(filmDto.getReleaseDate());
        film.setDuration(filmDto.getDuration());
        if (filmDto.getMpa() != null) {
            film.setMpa(filmDto.getMpa().getId());
        }
        if (filmDto.getGenres() != null) {
            filmDto.getGenres().forEach(genre -> film.getGenres().add(genre.getId()));
        }
        return film;
    }
}
