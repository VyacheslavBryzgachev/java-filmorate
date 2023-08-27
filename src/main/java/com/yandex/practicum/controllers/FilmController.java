package com.yandex.practicum.controllers;

import com.yandex.practicum.model.Film;
import com.yandex.practicum.storage.film.InMemoryFilmStorage;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
      return inMemoryFilmStorage.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@Valid @PathVariable int id, @PathVariable int userId) {
    inMemoryFilmStorage.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@Valid @PathVariable int id, @PathVariable int userId) {
        inMemoryFilmStorage.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@Valid @RequestParam (value = "count", defaultValue = "10", required = false) int count) {
        return inMemoryFilmStorage.getMostPopularFilms(count);
    }
}
