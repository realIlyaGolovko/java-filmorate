package ru.yandex.practicum.filmorate.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.user.FriendService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PutMapping("/{id}/friends/{friendId}")
    void addFriend(@PathVariable("id") final long userId, @PathVariable final long friendId) {
        friendService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    void delete(@PathVariable("id") final long userId, @PathVariable final long friendId) {
        friendService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    List<User> getFriends(@PathVariable("id") final long userId) {
        return friendService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    List<User> getCommonFriends(@PathVariable("id") final long userId,
                                @PathVariable("otherId") final long otherUserId) {
        return friendService.getCommonFriends(userId, otherUserId);
    }
}
