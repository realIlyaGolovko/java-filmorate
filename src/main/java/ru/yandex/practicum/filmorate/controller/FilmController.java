package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Marker;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmRepository filmRepository;

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public Film addFilm(@Valid @RequestBody final Film film) {
        log.info("Request POST /films {}", film);
        Film createdFilm = filmRepository.saveFilm(film);
        log.info("Response POST /films {}", createdFilm);
        return createdFilm;
    }

    @PutMapping
    @Validated(Marker.OnUpdate.class)
    public Film updateFilm(@Valid @RequestBody final Film film) {
        log.info("Request PUT /films {}", film);
        Film updatedFilm = filmRepository.updateFilm(film);
        log.info("Response PUT /films {}", updatedFilm);
        return updatedFilm;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Request GET /films");
        List<Film> films = filmRepository.getFilms();
        log.info("Response GET /films, count: {}", films.size());
        return films;
    }
}
