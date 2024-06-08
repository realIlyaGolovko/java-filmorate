package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface FriendRepository {
    void saveFriend(final User user, final User friend);

    void deleteFriend(final User user, final User friend);

    List<User> getFriends(final long userId);

    List<User> getCommonFriends(final User user, final User anotherUser);
}
