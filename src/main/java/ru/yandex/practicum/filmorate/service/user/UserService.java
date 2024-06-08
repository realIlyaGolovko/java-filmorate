package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface UserService {
    User createUser(final User user);

    User updateUser(final User user);

    List<User> getUsers();

    User getUser(final Long userId);

}
