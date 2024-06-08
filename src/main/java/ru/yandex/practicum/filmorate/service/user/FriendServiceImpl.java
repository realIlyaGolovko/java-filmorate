package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.repository.user.FriendRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public void addFriend(final long userId, final long friendId) {
        final User savedUser = getUserOrThrowError(userId);
        final User savedFriend = getUserOrThrowError(friendId);
        friendRepository.saveFriend(savedUser, savedFriend);
    }

    @Override
    public void deleteFriend(final long userId, final long friendId) {
        final User savedUser = getUserOrThrowError(userId);
        final User savedFriend = getUserOrThrowError(friendId);
        friendRepository.deleteFriend(savedUser, savedFriend);
    }

    @Override
    public List<User> getFriends(final long userId) {
        getUserOrThrowError(userId);
        return friendRepository.getFriends(userId);
    }

    @Override
    public List<User> getCommonFriends(final long userId, final long anotherUserId) {
        final User savedUser = getUserOrThrowError(userId);
        final User savedAnotherUser = getUserOrThrowError(anotherUserId);
        return friendRepository.getCommonFriends(savedUser, savedAnotherUser);
    }

    private User getUserOrThrowError(long userId) {
        return userRepository.getUser(userId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(userId));
    }
}
