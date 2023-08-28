package com.yandex.practicum.storage.film;

import com.yandex.practicum.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAllFilms();

    Film getFilmById(int id);

    Film createFilm(Film film);

    Film updateFilm(Film film);
}
