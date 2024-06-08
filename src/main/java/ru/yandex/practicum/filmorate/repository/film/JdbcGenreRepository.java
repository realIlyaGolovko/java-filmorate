package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations template;
    private final GenreRowMapper mapper;

    @Override
    public List<Genre> getAllGenres() {
        final String sqlQuery = "SELECT * " +
                "FROM genres " +
                "ORDER BY genre_id ";
        return template.query(sqlQuery, mapper);
    }

    @Override
    public Optional<Genre> getGenre(final long id) {
        final String sqlQuery = "SELECT * " +
                "FROM genres " +
                "WHERE genre_id = :id ";
        try {
            return Optional.ofNullable(template.queryForObject(sqlQuery, Map.of("id", id), mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
