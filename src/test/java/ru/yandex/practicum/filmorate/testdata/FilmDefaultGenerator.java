package ru.yandex.practicum.filmorate.testdata;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.util.StringGenerator;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

public class FilmDefaultGenerator {
    public static Film generateDefaultFilm() {
        Random random = new Random();
        return Film.builder()
                .name(StringGenerator.generateRandomString(10))
                .description(StringGenerator.generateRandomString(100))
                .releaseDate(LocalDate.of(1895, 12, 28).plusDays(random.nextLong(150L)))
                .duration(random.nextInt(0, 360))
                .mpa(new Mpa(1L, "G"))
                .genres(Set.of(new Genre(1L, "Комедия"), new Genre(2L, "Драма")))
                .build();
    }
}
