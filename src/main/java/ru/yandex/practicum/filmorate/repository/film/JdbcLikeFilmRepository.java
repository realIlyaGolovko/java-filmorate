package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcLikeFilmRepository implements LikeFilmRepository {
    private final NamedParameterJdbcOperations template;
    private final FilmRowMapper filmRowMapper;

    @Override
    public void addLike(final Film film, final User user) {
        final String sqlQuery = "MERGE INTO film_likes(film_id, user_id) " +
                "KEY (film_id, user_id) " +
                "VALUES (:filmId, :userId) ";
        template.update(sqlQuery,
                Map.of("filmId", film.getId(),
                        "userId", user.getId()));
    }

    @Override
    public void deleteLike(final Film film, final User user) {
        final String sqlQuery = "DELETE FROM film_likes " +
                "WHERE film_id = :filmId " +
                "AND user_id = :userId ";
        template.update(sqlQuery,
                Map.of("filmId", film.getId(),
                        "userId", user.getId()));
    }

    @Override
    public List<Film> getTopPopularFilms(int countOfFilms) {
        final String selectTopFilmsSqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.mpa_rating_id, mp.name AS mpa_name " +
                "FROM films f " +
                "JOIN mpa_ratings mp ON f.mpa_rating_id = mp.mpa_rating_id " +
                "JOIN ( " +
                "SELECT film_id, COUNT(film_id) AS cnt " +
                "FROM film_likes " +
                "GROUP BY film_id " +
                "ORDER BY COUNT(film_id) DESC " +
                "LIMIT :countOfFilms) AS fl " +
                "ON f.film_id = fl.film_id";
        return template.query(selectTopFilmsSqlQuery, Map.of("countOfFilms", countOfFilms), filmRowMapper);
    }
}
