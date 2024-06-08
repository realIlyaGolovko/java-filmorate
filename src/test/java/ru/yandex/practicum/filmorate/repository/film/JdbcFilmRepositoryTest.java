package ru.yandex.practicum.filmorate.repository.film;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.yandex.practicum.filmorate.testdata.FilmDefaultGenerator.generateDefaultFilm;

@JdbcTest
@Import({JdbcFilmRepository.class, FilmRowMapper.class, GenreRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcFilmRepositoryTest {
    private final JdbcFilmRepository sut;

    @Test
    void shouldSaveNewFilm() {
        final Film expected = generateDefaultFilm();

        Film actual = sut.saveFilm(expected);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldUpdateSavedFilm() {
        final long savedFilmId = createRandomFilm().getId();
        final Film expected = generateDefaultFilm();
        expected.setId(savedFilmId);

        Film actual = sut.updateFilm(expected);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnFilmById() {
        final Film expected = createRandomFilm();

        final Optional<Film> actual = sut.getFilm(expected.getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnAllFilms() {
        final List<Film> expected = List.of(createRandomFilm(), createRandomFilm());

        final List<Film> actual = sut.getFilms();

        assertThat(actual)
                .asList()
                .hasSize(expected.size())
                .containsExactlyElementsOf(expected);
    }

    private Film createRandomFilm() {
        return sut.saveFilm(generateDefaultFilm());
    }
}
