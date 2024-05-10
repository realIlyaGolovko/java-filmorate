package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class InMemoryFriendRepository implements FriendRepository {
    private final Map<Long, Set<User>> friends;

    @Override
    public void addFriend(final User user, final User friend) {
        friends.computeIfAbsent(user.getId(), id -> new HashSet<>())
                .add(friend);
        friends.computeIfAbsent(friend.getId(), id -> new HashSet<>())
                .add(user);
    }

    @Override
    public void deleteFriend(final User user, final User friend) {
        friends.getOrDefault(user.getId(), new HashSet<>())
                .remove(friend);
        friends.getOrDefault(friend.getId(), new HashSet<>())
                .remove(user);
    }

    @Override
    public Set<User> getFriends(final long userId) {
        return friends.getOrDefault(userId, new HashSet<>());
    }
}
