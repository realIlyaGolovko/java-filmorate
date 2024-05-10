package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class InMemoryLikeFilmRepository implements LikeFilmRepository {
    private final Map<Long, Set<User>> likes;

    @Override
    public void addLike(final Film film, final User user) {
        likes.computeIfAbsent(film.getId(), id -> new HashSet<>())
                .add(user);
    }

    @Override
    public void deleteLike(final Film film, final User user) {
        likes.getOrDefault(film.getId(), new HashSet<>())
                .remove(user);
    }

    @Override
    public Set<User> getFilmLikes(final Film film) {
        return likes.getOrDefault(film.getId(), new HashSet<>());
    }
}
