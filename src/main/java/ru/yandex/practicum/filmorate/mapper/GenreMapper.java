package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenreMapper {
    public GenreDto genreToGenreDto(Genre genre) {
        GenreDto result = new GenreDto();
        result.setId(genre.getId());
        result.setName(genre.getName());
        return result;
    }
}
