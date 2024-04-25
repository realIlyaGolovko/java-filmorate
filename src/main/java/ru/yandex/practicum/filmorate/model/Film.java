package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.validator.ReleaseDateConstraint;

import java.time.LocalDate;

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
}
