package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final UserMapper userMapper;
    private final FriendshipStorage friendshipStorage;

    private final String friendsPath = "/{id}/friends/{friendId}";

    /**
     * Получить пользователя по идентификатору
     */
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Integer id) {
        return userMapper.mapToUserDto(userStorage.getById(id));
    }

    /**
     * Создать пользователя
     */
    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        return userMapper.mapToUserDto(userStorage.add(userMapper.mapToUser(user)));
    }

    /**
     * Добавить в список друзей пользователя нового друга
     */
    @PutMapping(friendsPath)
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        friendshipStorage.addFriend(user, friend);
    }

    /**
     * Удалить пользователя из списка друзей для указанного пользователя
     */
    @DeleteMapping(friendsPath)
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        friendshipStorage.removeFriend(user, friend);
    }

    /**
     * Получить список друзей пользователя
     */
    @GetMapping("/{id}/friends")
    public List<UserDto> getFriends(@PathVariable Integer id) {
        User user = userStorage.getById(id);
        return friendshipStorage.getFriends(user).stream()
                .map(userMapper::mapToUserDto)
                .toList();
    }

    /**
     * Получить список общих друзей двух пользователей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        User user = userStorage.getById(id);
        User other = userStorage.getById(otherId);
        return friendshipStorage.getCommonFriends(user, other).stream()
                .map(userMapper::mapToUserDto)
                .toList();
    }

    /**
     * Обновить данные пользователя
     */
    @PutMapping
    public UserDto updateUser(@RequestBody User user) {
        return userMapper.mapToUserDto(userStorage.update(user));
    }

    /**
     * Получить список всех пользователей
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userStorage.getAll().stream().map(userMapper::mapToUserDto).toList();
    }
}
