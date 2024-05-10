package ru.yandex.practicum.filmorate.controller.film;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.LikeFilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class LikeFilmController {
    private final LikeFilmService likeFilmService;

    @PutMapping("/{id}/like/{userId}")
    void setLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        likeFilmService.setLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    void deleteLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        likeFilmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    List<Film> getTopPopularFilms(@RequestParam(value = "count", defaultValue = "10") int countOfFilms) {
        return likeFilmService.getTopPopularFilms(countOfFilms);
    }
}
