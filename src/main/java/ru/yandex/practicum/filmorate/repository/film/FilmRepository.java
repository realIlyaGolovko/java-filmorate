package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Film saveFilm(final Film film);

    Film updateFilm(final Film film);

    List<Film> getFilms();

    Optional<Film> getFilm(final Long filmId);
}
