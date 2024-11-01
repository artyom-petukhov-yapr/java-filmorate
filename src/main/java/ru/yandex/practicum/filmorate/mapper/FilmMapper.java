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
        FilmDto result = new FilmDto();
        result.setId(film.getId());
        result.setName(film.getName());
        result.setDescription(film.getDescription());
        result.setReleaseDate(film.getReleaseDate());
        result.setDuration(film.getDuration());
        if (film.getMpa() != null) {
            result.setMpa(mpaMapper.mpaToMpaDto(mpaStorage.getById(film.getMpa())));
        }
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre -> result.getGenres().add(genreMapper.genreToGenreDto(genreStorage.getById(genre))));
        }
        return result;
    }

    public Film mapToFilm(FilmDto filmDto) {
        Film result = new Film();
        result.setId(filmDto.getId());
        result.setName(filmDto.getName());
        result.setDescription(filmDto.getDescription());
        result.setReleaseDate(filmDto.getReleaseDate());
        result.setDuration(filmDto.getDuration());
        if (filmDto.getMpa() != null) {
            result.setMpa(filmDto.getMpa().getId());
        }
        if (filmDto.getGenres() != null) {
            filmDto.getGenres().forEach(genre -> result.getGenres().add(genre.getId()));
        }
        return result;
    }
}
