package ru.yandex.practicum.filmorate.testdata;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Random;

import static ru.yandex.practicum.filmorate.util.StringGenerator.generateRandomString;

public class UserDefaultGenerator {
    public static User generateDefaultFilm() {
        Random random = new Random();
        return User.builder()
                .email(String.format("%s@%s", generateRandomString(5), generateRandomString(5)))
                .login(generateRandomString(10))
                .name(generateRandomString(10))
                .birthday(LocalDate.now().minusDays(random.nextLong(0L, 100)))
                .build();
    }
}
