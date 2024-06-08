package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcFriendRepository implements FriendRepository {
    private final NamedParameterJdbcOperations template;
    private final UserRowMapper mapper;

    @Override
    public void saveFriend(final User user, final User friend) {
        if (!isHaveFriendRequest(user, friend)) {
            mergeFriend(user, friend);
        } else {
            mergeFriend(user, friend);
            mergeFriend(friend, user);
        }

    }

    private void mergeFriend(final User user, final User friend) {
        final String sqlQuery = "MERGE INTO FRIENDS (user_id, friend_id, updated) " +
                "KEY (user_id, friend_id) " +
                "VALUES (:userId, :friendId, :now) ";
        template.update(sqlQuery,
                Map.of("userId", user.getId(),
                        "friendId", friend.getId(),
                        "now", LocalDateTime.now()));
    }

    private boolean isHaveFriendRequest(final User user, final User friend) {
        final String sqlQuery = "SELECT user_id, friend_id " +
                "FROM friends " +
                "WHERE (user_id = :friendId AND friend_id = :userId) ";
        final SqlRowSet rowSet = template.queryForRowSet(sqlQuery,
                Map.of("userId", user.getId(), "friendId", friend.getId()));
        return rowSet.next();
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
        final String sqlQuery = "SELECT u.user_id, u.email, u.login, u.name, u.birthday " +
                "FROM users u " +
                "WHERE u.user_id IN " +
                "(SELECT f1.friend_id " +
                "FROM friends f1 " +
                "WHERE f1.user_id = :userId " +
                "INTERSECT " +
                "SELECT f2.friend_id " +
                "FROM friends f2 " +
                "WHERE f2.user_id = :anotherUserId)";
        return template.query(sqlQuery,
                Map.of("userId", user.getId(),
                        "anotherUserId", anotherUser.getId()),
                mapper);
    }
}
