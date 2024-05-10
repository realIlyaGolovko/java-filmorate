package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.FriendRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public void addFriend(final long userId, final long friendId) {
        final User savedUser = getUserOrThrowError(userId);
        final User savedFriend = getUserOrThrowError(friendId);
        friendRepository.addFriend(savedUser, savedFriend);
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
        return friendRepository.getFriends(userId).stream()
                .toList();
    }

    @Override
    public List<User> getCommonFriends(final long userId, final long otherUserId) {
        getUserOrThrowError(userId);
        getUserOrThrowError(otherUserId);
        final Set<User> userFriends = friendRepository.getFriends(userId);
        final Set<User> otherUserFriends = friendRepository.getFriends(otherUserId);
        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .toList();
    }

    private User getUserOrThrowError(long userId) {
        return userRepository.getUser(userId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(userId));
    }
}
