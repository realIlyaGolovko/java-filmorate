package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendRepository {
    void addFriend(final User user, final User friend);

    void deleteFriend(final User user, final User friend);

    Set<User> getFriends(final long userId);
}
