package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;

public interface MpaService {
    List<Mpa> getAllMpaRatings();

    Mpa getMpaRating(final long mpaRatingId);
}
