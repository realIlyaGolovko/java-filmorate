package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({JdbcMpaRepository.class, MpaRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcMpaRepositoryTest {
    private final JdbcMpaRepository sut;
    private final List<Mpa> mpaRatings = List.of(new Mpa(1L, "G"),
            new Mpa(2L, "PG"),
            new Mpa(3L, "PG-13"),
            new Mpa(4L, "R"),
            new Mpa(5L, "NC-17"));

    @Test
    void shouldReturnAllMpa() {
        final List<Mpa> actual = sut.getAllMpaRatings();

        assertThat(actual)
                .asList()
                .hasSize(mpaRatings.size())
                .containsExactlyElementsOf(mpaRatings);
    }

    @Test
    void shouldReturnMpaById() {
        final Mpa expected = mpaRatings.get(1);

        final Optional<Mpa> actual = sut.getMpaRating(expected.getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
