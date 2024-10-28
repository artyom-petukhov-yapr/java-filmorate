package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbc;
    private final UserRowMapper mapper;

    @Override
    public void addFriend(User user, User friend) {
        if (user.getId() == friend.getId()) {
            // игнорируем добавление в друзья самого себя
            return;
        }
        String query = "INSERT INTO FRIENDSHIP (ACCOUNT_ID, FRIEND_ID) VALUES (?, ?)";
        jdbc.update(query, user.getId(), friend.getId());
    }

    @Override
    public void removeFriend(User user, User friend) {
        String query = "DELETE " +
                "FROM FRIENDSHIP " +
                "WHERE ACCOUNT_ID = ? AND FRIEND_ID = ?";
        jdbc.update(query, user.getId(), friend.getId());
    }

    @Override
    public List<User> getFriends(User user) {
        String query = "SELECT A.ID, A.NAME, A.LOGIN, A.EMAIL, A.BIRTHDAY " +
                "FROM FRIENDSHIP F " +
                "JOIN ACCOUNT A ON A.ID = F.FRIEND_ID " +
                "WHERE F.ACCOUNT_ID = ?";
        return jdbc.query(query, mapper, user.getId());
    }

    @Override
    public List<User> getCommonFriends(User user, User other) {
        String query = "SELECT A.ID, A.NAME, A.LOGIN, A.EMAIL, A.BIRTHDAY " +
                "FROM FRIENDSHIP F1 " +
                "JOIN FRIENDSHIP F2 ON F2.FRIEND_ID = F1.FRIEND_ID " +
                "JOIN ACCOUNT A ON A.ID = F1.FRIEND_ID " +
                "WHERE F1.ACCOUNT_ID = ? AND F2.ACCOUNT_ID = ?";
        return jdbc.query(query, mapper, user.getId(), other.getId());
    }
}
