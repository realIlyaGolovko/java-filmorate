package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeFilmService {
    void setLike(final long filmId, final long userId);

    void deleteLike(final long filmId, final long userId);

    List<Film> getTopPopularFilms(final int countOfFilms);
}
