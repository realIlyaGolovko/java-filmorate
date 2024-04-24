package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.testdata.FilmDefaultGenerator;
import ru.yandex.practicum.filmorate.util.StringGenerator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты валидаций входных данных  для модели Film.")
public class FilmTest {
    private static final Validator validator;

    static {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.usingContext().getValidator();
        }
    }

    @Test
    @DisplayName("Не должно быть ошибок, если все поля заполнены корректными данными.")
    @Tag("Positive")
    void shouldNotBeValidationWhenAllFieldsAreFilledCorrectData() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();

        var validationSet = validator.validate(film);

        assertTrue(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Не должно быть ошибок, если необязательные поля null")
    @Tag("Positive")
    void shouldNotBeValidationWhenOptionalFieldsAreNull() {
        final Film film = Film.builder()
                .id(null)
                .name(StringGenerator.generateRandomString(10))
                .description(null)
                .releaseDate(null)
                .duration(null)
                .build();

        var validationSet = validator.validate(film);

        assertTrue(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Описание фильма может быть 200 символов.")
    @Tag("Positive")
    void shouldBeNotValidationWhenDescriptionIsEquals200() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setDescription(StringGenerator.generateRandomString(200));

        var validatorSet = validator.validate(film);

        assertTrue(validatorSet.isEmpty(), "Description's size can be 200 symbols.");
    }

    @Test
    @DisplayName("Продолжительность фильма может быть равна 0.")
    @Tag("Positive")
    void shouldNotBeValidationWhenDurationIsZero() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setDuration(0);

        var validatorSet = validator.validate(film);

        assertTrue(validatorSet.isEmpty(), "Duration can be equals zero.");
    }

    @Test
    @DisplayName("Должны быть ошибки, если обязательные поля null")
    @Tag("Negative")
    void shouldBeValidationWhenMandatoryFieldsAreNull() {
        final Film film = Film.builder()
                .id(null)
                .name(null)
                .description(null)
                .releaseDate(null)
                .duration(null)
                .build();

        var validationSet = validator.validate(film);

        assertFalse(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Название фильма не может состоять только из пробелов.")
    @Tag("Negative")
    void shouldBeValidationWhenNameContaintsOnlyGaps() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setName(" ");

        var validatorSet = validator.validate(film);
        var violation = validatorSet.iterator().next();

        assertEquals("name", violation.getPropertyPath().toString(), "Film name cannot be blank.");
        assertEquals("must not be blank", violation.getMessage(), "Film name cannot be blank.");
    }

    @Test
    @DisplayName("Название фильма не может быть пустой строкой.")
    @Tag("Negative")
    void shouldBeValidationWhenNameIsEmptyString() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setName("");

        var validatorSet = validator.validate(film);
        var violation = validatorSet.iterator().next();

        assertEquals("name", violation.getPropertyPath().toString(), "Film name cannot be blank.");
        assertEquals("must not be blank", violation.getMessage(), "Film name cannot be blank.");
    }


    @Test
    @DisplayName("Описание не может быть больше 200 символов.")
    @Tag("Negative")
    void shouldBeValidationWhenDescriptionIsBiggerThen200() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setDescription(StringGenerator.generateRandomString(201));

        var validatorSet = validator.validate(film);
        var violation = validatorSet.iterator().next();

        assertEquals("description", violation.getPropertyPath().toString(),
                "Description cannot be bigger 200 symbols");
        assertEquals("size must be between 0 and 200", violation.getMessage(),
                "Description cannot be bigger 200 symbols");
    }

    @Test
    @DisplayName("Дата релиза не может быть раньше 28.12.1895.")
    @Tag("Negative")
    void shouldBeValidationWhenReleaseDateIsUncorrect() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        var validatorSet = validator.validate(film);
        var violation = validatorSet.iterator().next();

        assertEquals("releaseDate", violation.getPropertyPath().toString(),
                "Release date cannot be earlier than 28.12.1895");
        assertEquals("Release date cannot be earlier than 28.12.1895", violation.getMessage());
    }

    @Test
    @DisplayName("Дата релиза может быть равна 28.12.1895.")
    @Tag("Negative")
    void shouldBeValidationWhenReleaseDateIsCorrect() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 28));

        var validatorSet = validator.validate(film);

        assertTrue(validatorSet.isEmpty(), "Release date can be equals 28.12.1895");
    }


    @Test
    @DisplayName("Продолжительность фильма не может быть отрицательной.")
    @Tag("Negative")
    void shouldBeValidationWhenDurationIsLessThanZero() {
        final Film film = FilmDefaultGenerator.generateDefaultFilm();
        film.setDuration(-1);

        var validatorSet = validator.validate(film);
        var violation = validatorSet.iterator().next();

        assertEquals("duration", violation.getPropertyPath().toString());
        assertEquals("must be greater than or equal to 0", violation.getMessage());
    }
}
