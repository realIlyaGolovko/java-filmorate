package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InMemoryFilmRepository implements FilmRepository {
    private final Map<Long, Film> films;
    private Long seq = 0L;

    @Override
    public Film saveFilm(final Film film) {
        film.setId(getSeq());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(final Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return films.values().stream()
                .toList();
    }

    @Override
    public Optional<Film> getFilm(final Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    private Long getSeq() {
        return ++seq;
    }
}
