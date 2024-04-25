package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository {
    Film saveFilm(final Film film);

    Film updateFilm(final Film film);

    List<Film> getFilms();
}
