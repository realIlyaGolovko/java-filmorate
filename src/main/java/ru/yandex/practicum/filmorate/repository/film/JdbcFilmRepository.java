package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations template;
    private final FilmRowMapper filmRowMapper;
    private final GenreRowMapper genreRowMapper;

    @Override
    public Film saveFilm(final Film film) {
        final String insertFilmSqlQuery = "INSERT INTO films ( " +
                "name, description, release_date, duration, mpa_rating_id ) " +
                "VALUES ( :name, :description, :releaseDate, :duration, :mpaId )";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId());
        template.update(insertFilmSqlQuery, params, keyHolder, new String[]{"film_id"});
        final long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        film.setId(filmId);
        saveFilmGenres(film);

        return getFilm(filmId)
                .orElseThrow(() -> new ValidationException(
                        String.format("Error while saving film %s", film.getId())));
    }

    private void saveFilmGenres(final Film film) {
        if (Objects.isNull(film.getGenres()) || film.getGenres().isEmpty()) {
            return;
        }
        final String mergeFilmGenreSqlQuery = "MERGE INTO film_genres ( film_id, genre_id ) " +
                "KEY ( film_id, genre_id ) " +
                "VALUES ( :filmId, :genreId )";
        var batchValues = film.getGenres().stream()
                .map(genre -> new MapSqlParameterSource()
                        .addValue("filmId", film.getId())
                        .addValue("genreId", genre.getId()))
                .toList();
        template.batchUpdate(mergeFilmGenreSqlQuery, batchValues.toArray(new SqlParameterSource[0]));
    }

    private void cleanFilmGenres(final Film film) {
        final String deleteFilmGenresSqlQuery = "DELETE FROM film_genres " +
                "WHERE film_id = :filmId";
        template.update(deleteFilmGenresSqlQuery, Map.of("filmId", film.getId()));
    }


    @Override
    public Film updateFilm(final Film film) {
        final String updateFilmSqlQuery = "UPDATE films " +
                "SET name = :name, " +
                "description = :description, " +
                "release_date = :releaseDate, " +
                "duration = :duration, " +
                "mpa_rating_id = :mpaId, " +
                "updated = :now " +
                "WHERE film_id = :filmId";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("filmId", film.getId())
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId())
                .addValue("now", LocalDateTime.now());
        template.update(updateFilmSqlQuery, params);
        cleanFilmGenres(film);
        saveFilmGenres(film);
        return getFilm(film.getId())
                .orElseThrow(() -> new ValidationException(
                        String.format("Error while saving film %s", film.getId())));
    }

    @Override
    public List<Film> getFilms() {
        final List<Genre> genres = getAllGenres();
        final String selectFilmsSqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, " +
                "f.duration, f.mpa_rating_id, m.name AS mpa_name " +
                "FROM films f " +
                "JOIN mpa_ratings m ON f.mpa_rating_id = m.mpa_rating_id";
        final List<Film> films = template.query(selectFilmsSqlQuery, filmRowMapper);

        final Map<Long, Set<Genre>> filmGenres = getAllFilmsGenres(genres);
        films.forEach(film -> film.setGenres(filmGenres.getOrDefault(film.getId(), new HashSet<>())));
        return films;
    }

    Map<Long, Set<Genre>> getAllFilmsGenres(final List<Genre> allGenres) {
        final Map<Long, Set<Genre>> filmGenres = new HashMap<>();
        final String selectFilmGenresSqlQuery = "SELECT film_id, genre_id FROM film_genres ";
        template.query(selectFilmGenresSqlQuery, rs -> {
                    while (rs.next()) {
                        final long filmId = rs.getLong("film_id");
                        final long genreId = rs.getLong("genre_id");
                        final Genre genre = allGenres.stream()
                                .filter(g -> g.getId() == genreId)
                                .findFirst()
                                .get();
                        filmGenres.computeIfAbsent(filmId, k -> new HashSet<>()).add(genre);
                    }
                }
        );
        return filmGenres;
    }

    private List<Genre> getAllGenres() {
        final String selectGenresSqlQuery = "SELECT genre_id, name FROM genres";
        return template.query(selectGenresSqlQuery, genreRowMapper);
    }

    @Override
    public Optional<Film> getFilm(final long filmId) {
        final String selectFilmSqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, " +
                "f.duration, f.mpa_rating_id, m.name AS mpa_name " +
                "FROM films f " +
                "JOIN mpa_ratings m ON f.mpa_rating_id = m.mpa_rating_id " +
                "WHERE f.film_id = :filmId";
        final Film film = template.queryForObject(selectFilmSqlQuery, Map.of("filmId", filmId), filmRowMapper);
        final Map<Long, Set<Genre>> filmGenres = getGenresByFilmId(filmId);
        Objects.requireNonNull(film).setGenres(filmGenres.getOrDefault(film.getId(), new HashSet<>()));
        return Optional.of(film);
    }


    private Map<Long, Set<Genre>> getGenresByFilmId(final long filmId) {
        final List<Genre> genres = getAllGenres();
        final String selectFilmGenresSqlQuery = "SELECT film_id, genre_id " +
                "FROM film_genres " +
                "WHERE film_id = :filmId " +
                "ORDER BY genre_id";
        final Map<Long, Set<Genre>> filmGenres = new HashMap<>();
        template.query(selectFilmGenresSqlQuery, Map.of("filmId", filmId), rs -> {
            try {
                final long genreId = rs.getLong("genre_id");
                final Genre genre = genres.stream()
                        .filter(g -> g.getId() == genreId)
                        .findFirst()
                        .get();
                filmGenres.computeIfAbsent(filmId, k -> new LinkedHashSet<>()).add(genre);
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(String.format("Error while getting genres by filmId %s", filmId));
            }
        });
        return filmGenres;
    }
}