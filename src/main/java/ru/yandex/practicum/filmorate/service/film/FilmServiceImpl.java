package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public Film createFilm(final Film film) {
        return filmRepository.saveFilm(film);
    }

    @Override
    public Film updateFilm(final Film film) {
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
}
