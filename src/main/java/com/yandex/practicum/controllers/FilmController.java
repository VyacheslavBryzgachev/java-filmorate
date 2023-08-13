package com.yandex.practicum.controllers;

import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    @ExceptionHandler(ValidationException.class)
    public Film createFilm(@Valid @RequestBody Film film) {
        setFilmIdAndCheckDate(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    @ExceptionHandler(ValidationException.class)
    public Film updateFilm(@RequestBody Film film) {
        setFilmIdAndCheckDate(film);
        if (films.containsKey(film.getId())) {
        films.put(film.getId(), film);
        }
        return film;
    }

    private void setFilmIdAndCheckDate(Film film) {
        LocalDate date = film.getReleaseDate();
        LocalDate localDateTime = LocalDate.of(1895, 12, 28);
        if (date.isBefore(localDateTime)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Дата выхода фильма не может быть раньше чем 1895-12-28");
        }
    }
}
