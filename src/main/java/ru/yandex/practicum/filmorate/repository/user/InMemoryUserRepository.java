package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users;
    private Long seq = 0L;

    @Override
    public User saveUser(final User user) {
        user.setId(getSeq());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(final User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return users.values().stream()
                .toList();
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    private Long getSeq() {
        return ++seq;
    }
}
