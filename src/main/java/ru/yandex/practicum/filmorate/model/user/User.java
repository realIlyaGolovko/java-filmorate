package ru.yandex.practicum.filmorate.model.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Marker;

import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class User {
    @Null(groups = {Marker.OnCreate.class})
    @NotNull(groups = {Marker.OnUpdate.class})
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}
