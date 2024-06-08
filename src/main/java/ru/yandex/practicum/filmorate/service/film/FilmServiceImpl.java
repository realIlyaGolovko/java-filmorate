package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.film.GenreRepository;
import ru.yandex.practicum.filmorate.repository.film.MpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final MpaRepository mpaRepository;

    @Override
    public Film createFilm(final Film film) {
        validateFilm(film);
        return filmRepository.saveFilm(film);
    }

    @Override
    public Film updateFilm(final Film film) {
        validateFilm(film);
        return filmRepository.getFilm(film.getId())
                .map(original -> {
                    filmRepository.updateFilm(film);
                    return film;
                })
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(film.getId()));
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.getFilms();
    }

    @Override
    public Film getFilm(final Long filmId) {
        return filmRepository.getFilm(filmId)
                .orElseThrow(() -> ExceptionUtils.createNotFoundException(filmId));
    }

    private void validateMpa(final Film film) {
        mpaRepository.getMpaRating(Optional.ofNullable(film.getMpa().getId()).get())
                .orElseThrow(() -> new ValidationException("Mpa is invalid or does not exist."));
    }

    private void validateGenre(final Film film) {
        final Set<Genre> genres = Optional.ofNullable(film.getGenres())
                .orElseGet(Collections::emptySet);
        if (genres.isEmpty()) {
            return;
        }
        final List<Genre> allGenres = genreRepository.getAllGenres();
        genres.forEach(genre -> {
                    Optional.ofNullable(genre)
                            .filter(allGenres::contains)
                            .orElseThrow(() -> new ValidationException("Genre is invalid or does not exist: " + genre));
                }
        );
    }

    private void validateFilm(final Film film) {
        validateMpa(film);
        validateGenre(film);
    }
}

