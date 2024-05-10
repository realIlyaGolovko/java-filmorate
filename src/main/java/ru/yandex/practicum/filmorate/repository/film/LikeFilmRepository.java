package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface LikeFilmRepository {
    void addLike(final Film film, final User user);

    void deleteLike(final Film film, final User user);

    Set<User> getFilmLikes(final Film film);
}
