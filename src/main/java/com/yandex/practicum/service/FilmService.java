package com.yandex.practicum.service;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.film.InMemoryFilmStorage;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film getFilmById(int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public void likeFilm(int userId, int filmId) {
        User user = inMemoryUserStorage.getUserById(userId);
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        if (user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + userId + " не найдено");
        } else if (film == null) {
            throw new UnknownIdException("Фильма с таким id=" + filmId + " не найдено");
        } else {
            film.getLikes().add(user.getId());
        }
    }

    public void deleteLike(int userId, int filmId) {
        User user = inMemoryUserStorage.getUserById(userId);
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        if (user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + userId + " не найдено");
        } else if (film == null) {
            throw new UnknownIdException("Фильма с таким id=" + filmId + " не найдено");
        } else {
            film.getLikes().remove(userId);
        }
    }

    public List<Film> getMostPopularFilms(int count) {
        List<Film> sortedFilms = new ArrayList<>(inMemoryFilmStorage.getAllFilms());
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
