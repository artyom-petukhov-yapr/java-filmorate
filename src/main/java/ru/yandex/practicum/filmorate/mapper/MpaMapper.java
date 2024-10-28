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
        MpaDto dto = new MpaDto();
        dto.setId(mpa.getId());
        dto.setName(mpa.getName());
        return dto;
    }

    public Mpa mpaDtoToMpa(MpaDto mpaDto) {
        Mpa mpa = new Mpa();
        mpa.setId(mpaDto.getId());
        mpa.setName(mpaDto.getName());
        return mpa;
    }
}
