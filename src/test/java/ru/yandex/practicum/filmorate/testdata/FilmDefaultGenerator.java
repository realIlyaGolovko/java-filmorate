package ru.yandex.practicum.filmorate.testdata;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.StringGenerator;

import java.time.LocalDate;
import java.util.Random;

public class FilmDefaultGenerator {
    public static Film generateDefaultFilm() {
        Random random = new Random();
        return Film.builder()
                .name(StringGenerator.generateRandomString(10))
                .description(StringGenerator.generateRandomString(100))
                .releaseDate(LocalDate.of(1895, 12, 28).plusDays(random.nextLong(150L)))
                .duration(random.nextInt(0, 360))
                .build();
    }
}
