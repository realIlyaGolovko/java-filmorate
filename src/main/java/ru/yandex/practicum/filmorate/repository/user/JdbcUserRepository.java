package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcOperations template;
    private final UserRowMapper mapper;

    @Override
    public User saveUser(final User user) {
        final String sqlQuery = "INSERT INTO users(email, login, name, birthday) " +
                "VALUES(:email, :login, :name, :birthday) ";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());
        template.update(sqlQuery, params, keyHolder, new String[]{"user_id"});
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User updateUser(final User user) {
        final String sqlQuery = "UPDATE users " +
                "SET email = :email, " +
                "login = :login, " +
                "name = :name, " +
                "birthday = :birthday, " +
                "updated = :now " +
                "WHERE user_id = :userId ";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday())
                .addValue("userId", user.getId())
                .addValue("now", LocalDateTime.now());

        template.update(sqlQuery, params);
        return user;
    }

    @Override
    public List<User> getUsers() {
        final String sqlQuery = "SELECT user_id, email, login, name, birthday " +
                "FROM users ";
        return template.query(sqlQuery, mapper);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        final String sqlQuery = "SELECT user_id, email, login, name, birthday " +
                "FROM users " +
                "WHERE user_id = :userId ";
        try {
            return Optional.ofNullable(template.queryForObject(sqlQuery, Map.of("userId", userId), mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
