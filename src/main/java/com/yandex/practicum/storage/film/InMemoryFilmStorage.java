package com.yandex.practicum.storage.film;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    Map<Integer, Film> films = new HashMap<>();
    private final InMemoryUserStorage inMemoryUserStorage;

    int id = 1;

    @Autowired
    public InMemoryFilmStorage(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        Film film = films.get(id);
        if(film == null) {
            throw new UnknownIdException("Фильма с таким id=" + id +  " не найдено");
        }
        return film;
    }

    @Override
    public Film createFilm(Film film) {
        checkFilmDate(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        checkFilmDate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        }
        else {
            throw new UnknownIdException("Такого id=" + film.getId() + " не найдено");
        }
        return film;
    }

    @Override
    public void likeFilm(int userId, int filmId) {
        User user = inMemoryUserStorage.getUserById(userId);
        Film film = films.get(filmId);
        if (user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + userId + " не найдено");
        } else if (film == null) {
            throw new UnknownIdException("Фильма с таким id=" + filmId + " не найдено");
        } else {
            film.setLikes(film.getLikes() + 1);
        }
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        User user = inMemoryUserStorage.getUserById(userId);
        Film film = films.get(filmId);
        if(user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + userId + " не найдено");
        }
        else if (film == null) {
            throw new UnknownIdException("Фильма с таким id=" + filmId + " не найдено");
        }
        else {
            film.setLikes(film.getLikes() - 1);
        }
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        List<Film> sortedFilms = new ArrayList<>(films.values());
        Collections.sort(sortedFilms);
        if(sortedFilms.size() < count) {
            count = sortedFilms.size();
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

    private int getNextId() {
        return id++;
    }
}
