package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(final User user) {
        return Optional.ofNullable(users.get(user.getId()))
                .map(original -> {
                    users.put(user.getId(), user);
                    return user;
                })
                .orElseGet(() -> saveUser(user));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private Long getSeq() {
        return ++seq;
    }
}
