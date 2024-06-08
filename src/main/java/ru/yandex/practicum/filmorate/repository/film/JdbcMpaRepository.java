package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMpaRepository implements MpaRepository {
    private final NamedParameterJdbcOperations template;
    private final MpaRowMapper mapper;

    @Override
    public List<Mpa> getAllMpaRatings() {
        final String sqlQuery = "SELECT mpa_rating_id,name " +
                "FROM mpa_ratings " +
                "ORDER BY mpa_rating_id ";
        return template.query(sqlQuery, mapper);
    }

    @Override
    public Optional<Mpa> getMpaRating(final long id) {
        final String sqlQuery = "SELECT mpa_rating_id, name " +
                "FROM mpa_ratings " +
                "WHERE mpa_rating_id = :id";
        try {
            return Optional.ofNullable(template.queryForObject(sqlQuery, Map.of("id", id), mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
