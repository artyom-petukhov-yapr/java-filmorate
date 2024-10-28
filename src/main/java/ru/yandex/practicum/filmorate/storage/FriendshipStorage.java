package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем данных о друзьях
 */
public interface FriendshipStorage {
    void addFriend(User user, User friend);

    void removeFriend(User user, User friend);

    List<User> getFriends(User user);

    List<User> getCommonFriends(User user, User other);
}
