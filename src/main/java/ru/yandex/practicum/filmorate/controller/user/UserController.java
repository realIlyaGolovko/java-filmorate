package ru.yandex.practicum.filmorate.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Marker;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated({Marker.OnCreate.class})
    public User createUser(@Valid @RequestBody final User user) {
        return userService.createUser(user);
    }

    @PutMapping
    @Validated({Marker.OnUpdate.class})
    public User updateUser(@Valid @RequestBody final User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") final long userId) {
        return userService.getUser(userId);
    }
}
