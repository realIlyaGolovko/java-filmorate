package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcFriendRepository implements FriendRepository {
    private final NamedParameterJdbcOperations template;
    private final UserRowMapper mapper;

    @Override
    public void saveFriend(final User user, final User friend) {
        final String sqlQuery = "MERGE INTO FRIENDS " +
                "VALUES (:userId, :friendId ) ";
        template.update(sqlQuery,
                Map.of("userId", user.getId(),
                        "friendId", friend.getId()));
    }

    @Override
    public void deleteFriend(final User user, final User friend) {
        deleteRow(user, friend);
    }

    private void deleteRow(final User user, final User friend) {
        final String sqlQuery = "DELETE FROM friends " +
                "WHERE user_id = :userId " +
                "AND friend_id = :friendId ";
        template.update(sqlQuery, Map.of("userId", user.getId(), "friendId", friend.getId()));
    }

    @Override
    public List<User> getFriends(final long userId) {
        final String sqlQuery = "SELECT u.user_id, u.email, u.login, u.name, u.birthday " +
                "FROM users u " +
                "JOIN friends f on u.user_id = f.friend_id " +
                "WHERE f.user_id = :userId";
        return template.query(sqlQuery, Map.of("userId", userId), mapper);
    }

    @Override
    public List<User> getCommonFriends(User user, User anotherUser) {
        final String sqlQuery = "SELECT * " +
                "FROM USERS u, " +
                "FRIENDS f, " +
                "FRIENDS o " +
                "WHERE u.USER_ID = f.FRIEND_ID " +
                "AND u.USER_ID = o.FRIEND_ID " +
                "AND f.USER_ID = :userId " +
                "AND o.USER_ID = :anotherUserId ";
        return template.query(sqlQuery,
                Map.of("userId", user.getId(),
                        "anotherUserId", anotherUser.getId()),
                mapper);
    }
}
