package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface LikeFilmRepository {
    void addLike(final Film film, final User user);

    void deleteLike(final Film film, final User user);

    List<Film> getTopPopularFilms(final int countOfFilms);
}
