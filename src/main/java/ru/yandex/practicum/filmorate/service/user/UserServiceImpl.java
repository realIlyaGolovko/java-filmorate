package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        updateNameIfNull(user);
        return userRepository.saveUser(user);
    }

    @Override
    public User updateUser(final User user) {
        return userRepository.getUser(user.getId())
                .map(original -> {
                    updateNameIfNull(user);
                    userRepository.updateUser(user);
                    return user;
                })
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(user.getId()));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User getUser(final Long userId) {
        return userRepository.getUser(userId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(userId));
    }

    private void updateNameIfNull(final User user) {
        if (Objects.isNull(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
