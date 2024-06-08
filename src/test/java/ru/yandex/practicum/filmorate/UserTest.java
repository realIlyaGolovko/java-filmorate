package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.testdata.UserDefaultGenerator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.util.StringGenerator.generateRandomString;

@DisplayName("Тесты валидаций входных данных  для модели User.")
public class UserTest {
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
        final User user = UserDefaultGenerator.generateDefaultUser();

        var validationSet = validator.validate(user);

        assertTrue(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Не должно быть ошибок, если необязательные поля null.")
    @Tag("Positive")
    void shouldNotBeValidationWhenOptionalFieldsAreNull() {
        final User user = User.builder()
                .id(null)
                .email(String.format("%s@%s", generateRandomString(5), generateRandomString(5)))
                .login(generateRandomString(5))
                .name(null)
                .birthday(null)
                .build();

        var validationSet = validator.validate(user);

        assertTrue(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Дата рождения  может быть сегодня.")
    @Tag("Positive")
    void shouldNotBeValidationWhenBirthdayIsToday() {
        final User user = UserDefaultGenerator.generateDefaultUser();
        user.setBirthday(LocalDate.now());

        var validationSet = validator.validate(user);

        assertTrue(validationSet.isEmpty());
    }


    @Test
    @DisplayName("Должны быть ошибки, если обязательные поля null.")
    @Tag("Negative")
    void shouldBeValidationWhenMandatoryFieldsAreNull() {
        final User user = User.builder()
                .id(null)
                .email(null)
                .login(null)
                .name(null)
                .birthday(null)
                .build();

        var validationSet = validator.validate(user);

        assertFalse(validationSet.isEmpty());
    }

    @Test
    @DisplayName("Емейл должен содержать символ @.")
    @Tag("Negative")
    void shouldBeValidationWhenEmailIsUncorrect() {
        final User user = UserDefaultGenerator.generateDefaultUser();
        user.setEmail(generateRandomString(10));

        var validationSet = validator.validate(user);
        var violation = validationSet.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("must be a well-formed email address", violation.getMessage());
    }

    @Test
    @DisplayName("Логин не должен содержать пробелы.")
    @Tag("Negative")
    void shouldBeValidationWhenLoginContainsGaps() {
        final User user = UserDefaultGenerator.generateDefaultUser();
        user.setLogin(String.format(" %s ", generateRandomString(10)));

        var validationSet = validator.validate(user);
        var violation = validationSet.iterator().next();

        assertEquals("login", violation.getPropertyPath().toString());
        assertEquals("must match \"^\\S+$\"", violation.getMessage(), "Login cannot contains gaps.");
    }

    @Test
    @DisplayName("Дата рождения не может быть в будущем.")
    @Tag("Negative")
    void shouldBeValidationWhenBirthdayInTheFuture() {
        final User user = UserDefaultGenerator.generateDefaultUser();
        user.setBirthday(LocalDate.now().plusDays(1));

        var validationSet = validator.validate(user);
        var violation = validationSet.iterator().next();

        assertEquals("birthday", violation.getPropertyPath().toString());
        assertEquals("must be a date in the past or in the present", violation.getMessage(),
                "Birthday cannot be in the future");
    }
}
