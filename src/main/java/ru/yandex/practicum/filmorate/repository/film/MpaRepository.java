package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaRepository {
    List<Mpa> getAllMpaRatings();

    Optional<Mpa> getMpaRating(final long id);
}
