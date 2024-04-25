package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {
    User saveUser(final User user);

    User updateUser(final User user);

    List<User> getUsers();
}
