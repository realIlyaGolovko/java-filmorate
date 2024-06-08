package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User saveUser(final User user);

    User updateUser(final User user);

    List<User> getUsers();

    Optional<User> getUser(final Long userId);
}
