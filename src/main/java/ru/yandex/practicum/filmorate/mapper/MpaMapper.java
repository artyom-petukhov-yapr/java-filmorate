package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MpaMapper {
    public MpaDto mpaToMpaDto(Mpa mpa) {
        MpaDto result = new MpaDto();
        result.setId(mpa.getId());
        result.setName(mpa.getName());
        return result;
    }

    public Mpa mpaDtoToMpa(MpaDto mpaDto) {
        Mpa result = new Mpa();
        result.setId(mpaDto.getId());
        result.setName(mpaDto.getName());
        return result;
    }
}
