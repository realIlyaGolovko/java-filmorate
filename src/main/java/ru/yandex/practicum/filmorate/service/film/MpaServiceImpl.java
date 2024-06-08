package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionUtils;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.repository.film.MpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaRepository mpaRepository;

    @Override
    public List<Mpa> getAllMpaRatings() {
        return mpaRepository.getAllMpaRatings();
    }

    @Override
    public Mpa getMpaRating(long mpaRatingId) {
        return mpaRepository.getMpaRating(mpaRatingId).orElseThrow(
                () -> ExceptionUtils.createNotFoundException(mpaRatingId));
    }
}
