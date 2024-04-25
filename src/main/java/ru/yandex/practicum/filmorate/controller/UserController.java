package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Marker;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public User createUser(@Valid @RequestBody final User user) {
        log.info("Request POST /users {}", user);
        User createdUser = userRepository.saveUser(user);
        log.info("Response POST /users {}", createdUser);
        return createdUser;
    }

    @PutMapping
    @Validated({Marker.OnUpdate.class})
    public User updateUser(@Valid @RequestBody final User user) {
        log.info("Request PUT /users {}", user);
        User updatedUser = userRepository.updateUser(user);
        log.info("Response PUT /users {}", updatedUser);
        return updatedUser;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Request GET /users");
        List<User> users = userRepository.getUsers();
        log.info("Response GET /users, count: {}", users.size());
        return users;
    }
}
