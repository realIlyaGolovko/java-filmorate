package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users;
    private Long seq;

    public UserRepositoryImpl(Map<Long, User> users) {
        this.users = users;
        seq = 0L;
    }

    @Override
    public User saveUser(final User user) {
        user.setId(getSeq());
        updateNameIfNull(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(final User user) {
        return Optional.ofNullable(users.get(user.getId()))
                .map(original -> {
                    updateNameIfNull(user);
                    users.put(user.getId(), user);
                    return user;
                })
                .orElseThrow(() -> new ValidationException(String.format("id %s not founded", user.getId())));
    }

    private User updateNameIfNull(final User user) {
        if (Objects.isNull(user.getName())) {
            user.setName(user.getLogin());
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private Long getSeq() {
        return ++seq;
    }
}
