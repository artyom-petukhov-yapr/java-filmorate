package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    private final String friendsPath = "/{id}/friends/{friendId}";

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userStorage.getById(id);
    }

    /**
     * Создание пользователя
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userStorage.add(user);
    }

    @PutMapping(friendsPath)
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        userService.addFriend(user, friend);
    }

    @DeleteMapping(friendsPath)
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        userService.removeFriend(user, friend);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Integer id) {
        User user = userStorage.getById(id);
        Set<Integer> friends = userService.getFriends(user);

        return userStorage.getAll().stream()
                .filter(u -> friends.contains(u.getId()))
                .toList();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        User user = userStorage.getById(id);
        User other = userStorage.getById(otherId);
        Set<Integer> commonFriends = userService.getCommonFriends(user, other);

        return userStorage.getAll().stream()
                .filter(u -> commonFriends.contains(u.getId()))
                .toList();
    }

    /**
     * Обновление пользователя
     */
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userStorage.update(user);
    }

    /**
     * @return Все пользователи
     */
    @GetMapping
    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }
}
