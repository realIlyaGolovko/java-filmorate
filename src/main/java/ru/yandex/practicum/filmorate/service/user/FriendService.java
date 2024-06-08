package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface FriendService {
    void addFriend(final long userId, final long friendId);

    void deleteFriend(final long userId, final long friendId);

    List<User> getFriends(final long userId);

    List<User> getCommonFriends(final long userId, final long anotherUserId);
}
