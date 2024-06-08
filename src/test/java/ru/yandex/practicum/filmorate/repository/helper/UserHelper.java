package ru.yandex.practicum.filmorate.repository.helper;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import static ru.yandex.practicum.filmorate.testdata.UserDefaultGenerator.generateDefaultUser;

@RequiredArgsConstructor
public class UserHelper {
    private final UserRepository userRepository;

    public User createRandomUser() {
        return userRepository.saveUser(generateDefaultUser());
    }

}
