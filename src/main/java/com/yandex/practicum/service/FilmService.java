package com.yandex.practicum.service;

import com.yandex.practicum.dao.FilmDbStorage;
import com.yandex.practicum.dao.LikedFilmsDbStorage;
import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final LikedFilmsDbStorage likedFilmsDbStorage;

    public List<Film> getAllFilms() {
        List<Film> allFilms = filmDbStorage.getAllFilms();
        return allFilms;
    }

    public Film getFilmById(int id) {
        Film film = filmDbStorage.getFilmById(id);
        return film;
    }

    public Film createFilm(Film film) {
        checkFilmDate(film);
        film = filmDbStorage.createFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        checkFilmDate(film);
        film = filmDbStorage.updateFilm(film);
        return film;
    }

    public void likeFilm(int userId, int filmId) {
        likedFilmsDbStorage.likeFilm(userId, filmId);
    }

    public void deleteLike(int userId, int filmId) {
        likedFilmsDbStorage.deleteLike(userId, filmId);
    }

    public List<Film> getMostPopularFilms(int count) {
        List<Film> sortedFilms = new ArrayList<>(getAllFilms());
        sortedFilms.sort(Film.COMPARE_BY_LIKES);
        int size = sortedFilms.size();
        if (sortedFilms.size() < count) {
            count = size;
        } else if (sortedFilms.size() > count) {
            size = count;
            return sortedFilms.subList(0, size);
        }
        return sortedFilms.subList(0, count);
    }

    private void checkFilmDate(Film film) {
        LocalDate date = film.getReleaseDate();
        LocalDate localDateTime = LocalDate.of(1895, 12, 28);
        if (date.isBefore(localDateTime)) {
            throw new ValidationException("Дата выхода фильма не может быть раньше чем 1895-12-28");
        }
    }
}
