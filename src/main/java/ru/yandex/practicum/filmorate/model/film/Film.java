package ru.yandex.practicum.filmorate.model.film;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Marker;
import ru.yandex.practicum.filmorate.validator.ReleaseDateConstraint;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class Film {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @PositiveOrZero
    private Integer duration;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres;
}
