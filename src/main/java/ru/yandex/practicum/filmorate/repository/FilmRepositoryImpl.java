package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FilmRepositoryImpl implements FilmRepository {
    private final Map<Long, Film> films;
    private Long seq;

    public FilmRepositoryImpl(Map<Long, Film> films) {
        this.films = films;
        seq = 0L;
    }

    @Override
    public Film saveFilm(final Film film) {
        film.setId(getSeq());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(final Film film) {
        return Optional.ofNullable(films.get(film.getId()))
                .map(original -> {
                    films.put(film.getId(), film);
                    return film;
                })
                .orElseGet(() -> saveFilm(film));
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    private Long getSeq() {
        return ++seq;
    }
}
