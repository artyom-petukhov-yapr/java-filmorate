package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public UserDto mapToUserDto(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setLogin(user.getLogin());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setBirthday(user.getBirthday());
        return result;
    }

    public User mapToUser(UserDto user) {
        User result = new User();
        result.setId(user.getId());
        result.setLogin(user.getLogin());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setBirthday(user.getBirthday());
        return result;
    }
}