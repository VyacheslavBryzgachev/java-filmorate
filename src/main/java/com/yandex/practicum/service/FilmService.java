package com.yandex.practicum.service;

import com.yandex.practicum.dao.FilmDbStorage;
import com.yandex.practicum.dao.LikedFilmsDbStorage;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.Genre;
import com.yandex.practicum.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final LikedFilmsDbStorage likedFilmsDbStorage;
    private final MpaService mpaService;

    public List<Film> getAllFilms() {
        List<Film> allFilms = filmDbStorage.getAllFilms();
        for (Film film : allFilms) {
            Mpa mpa = mpaService.getMpaById(film.getMpa().getId());
            film.setMpa(mpa);
        }
        return allFilms;
    }

    public Film getFilmById(int id) {
        Film film = filmDbStorage.getFilmById(id);
        Mpa mpa = mpaService.getMpaById(film.getMpa().getId());
        film.setMpa(mpa);
        return film;
    }

    public Film createFilm(Film film) {
        film = filmDbStorage.createFilm(film);
        Mpa mpa = mpaService.getMpaById(film.getMpa().getId());
        List<Genre> genres = film.getGenres();
        film.setGenres(genres);
        film.setMpa(mpa);
        return film;
    }

    public Film updateFilm(Film film) {
        film = filmDbStorage.updateFilm(film);
        Mpa mpa = mpaService.getMpaById(film.getMpa().getId());
        List<Genre> genres = film.getGenres();
        film.setMpa(mpa);
        film.setGenres(genres);
        return film;
    }

    public void likeFilm(int userId, int filmId) {
        likedFilmsDbStorage.likeFilm(userId, filmId);
        Film film = filmDbStorage.getFilmById(filmId);
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.add(userId);
    }

    public void deleteLike(int userId, int filmId) {
        likedFilmsDbStorage.deleteLike(userId, filmId);
        Film film = filmDbStorage.getFilmById(filmId);
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.remove(userId);
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
}
