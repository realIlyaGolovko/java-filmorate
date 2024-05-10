package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.film.LikeFilmRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeFilmServiceImpl implements LikeFilmService {
    private final LikeFilmRepository likeFilmRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public void setLike(final long filmId, final long userId) {
        final Film savedFilm = getFilmOrThrowError(filmId);
        final User savedUser = getUserOrThrowError(userId);
        likeFilmRepository.addLike(savedFilm, savedUser);
    }

    @Override
    public void deleteLike(final long filmId, final long userId) {
        final Film savedFilm = getFilmOrThrowError(filmId);
        final User savedUser = getUserOrThrowError(userId);
        likeFilmRepository.deleteLike(savedFilm, savedUser);
    }

    @Override
    public List<Film> getTopPopularFilms(final int countOfFilms) {
        return filmRepository.getFilms().stream()
                .sorted(Comparator.comparingInt(film -> likeFilmRepository.getFilmLikes((Film) film).size())
                        .reversed())
                .limit(countOfFilms)
                .toList();
    }

    private Film getFilmOrThrowError(long filmId) {
        return filmRepository.getFilm(filmId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(filmId));
    }

    private User getUserOrThrowError(long userId) {
        return userRepository.getUser(userId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(userId));
    }
}
