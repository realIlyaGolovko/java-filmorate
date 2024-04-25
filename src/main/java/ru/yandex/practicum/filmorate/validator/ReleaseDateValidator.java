package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {
    public static final String CINEMA_BIRTHDAY_AS_STRING = "1895-12-28";
    public static final LocalDate CINEMA_BIRTHDAY = LocalDate.parse(CINEMA_BIRTHDAY_AS_STRING,
            DateTimeFormatter.ISO_DATE);


    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(releaseDate)) {
            return true;
        }
        return !releaseDate.isBefore(CINEMA_BIRTHDAY);
    }
}
