package com.yandex.practicum.filmStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.FilmDbStorage;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.Genre;
import com.yandex.practicum.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageTest {

    private final FilmDbStorage filmDbStorage;
    private Film film = new Film()
            .withName("Остров сокровищ")
            .withDescription("Описание фильма")
            .withReleaseDate(LocalDate.of(1989, 04, 17))
            .withDuration(100)
            .withGenres(List.of(new Genre(1, "Комедия")))
            .withMpa(new Mpa(1, null));

    private Film upgradedFilm = new Film()
            .withId(1)
            .withName("Обновленный Остров сокровищ")
            .withDescription("Обновленное Описание фильма")
            .withReleaseDate(LocalDate.of(1989, 04, 17))
            .withDuration(120)
            .withGenres(List.of(new Genre(2, "Драма")))
            .withMpa(new Mpa(3, null));

    @BeforeEach
    public void add() {
        filmDbStorage.createFilm(film);
        filmDbStorage.updateFilm(upgradedFilm);
    }

    @Test
    public void testCreateFilm() {
        Film getFilm = filmDbStorage.getFilmById(1);
        Assertions.assertEquals("Обновленный Остров сокровищ", getFilm.getName());
        Assertions.assertEquals("Обновленное Описание фильма", getFilm.getDescription());
        Assertions.assertEquals(LocalDate.of(1989, 04, 17), getFilm.getReleaseDate());
        Assertions.assertEquals(120, getFilm.getDuration());
        Assertions.assertEquals(List.of(new Genre(2, "Драма")), getFilm.getGenres());
        Assertions.assertEquals(new Mpa(3, null), getFilm.getMpa());
    }

    @Test
    public void testGetAllFilms() {
        Assertions.assertFalse(filmDbStorage.getAllFilms().isEmpty());
    }

    @Test
    public void testGelFilmById() {
        Assertions.assertEquals(filmDbStorage.getFilmById(1), filmDbStorage.getFilmById(upgradedFilm.getId()));
    }

    @Test
    public void testUpgradeFilm() {
        Film film = filmDbStorage.getFilmById(1);
        Assertions.assertEquals("Обновленный Остров сокровищ", film.getName());
        Assertions.assertEquals("Обновленное Описание фильма", film.getDescription());
        Assertions.assertEquals(LocalDate.of(1989, 04, 17), film.getReleaseDate());
        Assertions.assertEquals(120, film.getDuration());
        Assertions.assertEquals(List.of(new Genre(2, "Драма")), film.getGenres());
        Assertions.assertEquals(new Mpa(3, null), film.getMpa());
    }
}
