package com.yandex.practicum.controllers;

import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.Film;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {

    List<Film> films = new ArrayList<>();
    int id = 1;

    @RequestMapping(path = "/films", method = RequestMethod.GET)
    public List<Film> getAllFilms() {
        return films;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        LocalDate date = film.getReleaseDate();
        LocalDate localDateTime = LocalDate.of(1895, 12, 28);
        if(date.isBefore(localDateTime)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Дата выхода фильма не может быть раньше чем 1895-12-28");
        }
        films.add(film);
        return film;
    }

    @RequestMapping(path = "/update", method = RequestMethod.PATCH)
    public Film updateFilm(@RequestBody Film film) {
        int id = film.getId();
        for(Film f : films) {
            if (f.getId() == id) {
                films.remove(f);
                films.add(film);
            }
        }
        return film;
    }

    private int getNextId() {
        return id++;
    }
}
