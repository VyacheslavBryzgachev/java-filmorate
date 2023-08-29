package com.yandex.practicum.storage.film;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    Map<Integer, Film> films = new HashMap<>();
    int id = 1;

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        Film film = films.get(id);
        if (film == null) {
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
        } else {
            throw new UnknownIdException("Такого id=" + film.getId() + " не найдено");
        }
        return film;
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
