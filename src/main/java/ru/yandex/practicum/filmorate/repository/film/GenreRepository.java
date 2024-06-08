package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAllGenres();

    Optional<Genre> getGenre(final long id);
}
