package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmService {
    Film createFilm(final Film film);

    Film updateFilm(final Film film);

    List<Film> getFilms();

    Film getFilm(final Long filmId);
}
