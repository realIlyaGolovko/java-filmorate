package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.yandex.practicum.filmorate.validator.ReleaseDateValidator.CINEMA_BIRTHDAY_AS_STRING;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateConstraint {
    String message() default "Release date cannot be earlier than " + CINEMA_BIRTHDAY_AS_STRING;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
