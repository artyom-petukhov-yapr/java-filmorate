package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final HashMap<Integer, Set<Integer>> userFriends = new HashMap<>();

    public void addFriend(User user, User friend) {
        if (user.getId() == friend.getId()) {
            // игнорируем добавление в друзья самого себя
            return;
        }
        // для userId добавляем friendId в список друзей
        userFriends.computeIfAbsent(user.getId(), k -> new HashSet<>()).add(friend.getId());
        // для friendId добавляем userId в список друзей
        userFriends.computeIfAbsent(friend.getId(), k -> new HashSet<>()).add(user.getId());
    }

    public void removeFriend(User user, User friend) {
        // для userId удаляем friendId из списка друзей
        userFriends.computeIfAbsent(user.getId(), k -> new HashSet<>()).remove(friend.getId());
        // для friendId удаляем userId из списка друзей
        userFriends.computeIfAbsent(friend.getId(), k -> new HashSet<>()).remove(user.getId());
    }

    public Set<Integer> getFriends(User user) {
        return userFriends.getOrDefault(user.getId(), new HashSet<>());
    }

    public Set<Integer> getCommonFriends(User user, User other) {
        if (!userFriends.containsKey(user.getId()) || !userFriends.containsKey(other.getId())) {
            return new HashSet<>();
        }
        // создаем новый сет для формирования списка общих друзей - за основу берем друзей пользователя user
        Set<Integer> commonFriends = new HashSet<>(userFriends.get(user.getId()));
        // оставляем в сете только тех друзей, которые также являются друзьями у other
        commonFriends.retainAll(userFriends.get(other.getId()));

        return commonFriends;
    }
}
