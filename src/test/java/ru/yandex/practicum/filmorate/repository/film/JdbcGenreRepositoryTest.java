package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({JdbcGenreRepository.class, GenreRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcGenreRepositoryTest {
    private final JdbcGenreRepository sut;
    private final List<Genre> genres = List.of(new Genre(1L, "Комедия"),
            new Genre(2L, "Драма"),
            new Genre(3L, "Мультфильм"),
            new Genre(4L, "Триллер"),
            new Genre(5L, "Документальный"),
            new Genre(6L, "Боевик"));

    @Test
    void shouldReturnAllGenres() {
        final List<Genre> actual = sut.getAllGenres();

        assertThat(actual)
                .asList()
                .hasSize(genres.size())
                .containsExactlyElementsOf(genres);
    }

    @Test
    void shouldReturnGenreById() {
        final Optional<Genre> actual = sut.getGenre(genres.get(0).getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(genres.get(0));
    }
}
