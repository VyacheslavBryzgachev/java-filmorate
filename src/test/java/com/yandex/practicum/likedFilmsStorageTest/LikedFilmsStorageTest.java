package com.yandex.practicum.likedFilmsStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.FilmDbStorage;
import com.yandex.practicum.dao.LikedFilmsDbStorage;
import com.yandex.practicum.dao.UserDbStorage;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.Genre;
import com.yandex.practicum.model.Mpa;
import com.yandex.practicum.model.User;
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
public class LikedFilmsStorageTest {
    private final LikedFilmsDbStorage likedFilmsDbStorage;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private User user = new User()
            .withEmail("test@test.ru")
            .withLogin("Login test")
            .withName("Name test")
            .withBirthday(LocalDate.of(1992, 10, 20));
    private Film film = new Film()
            .withName("Остров сокровищ")
            .withDescription("Описание фильма")
            .withReleaseDate(LocalDate.of(1989, 04, 17))
            .withDuration(100)
            .withGenres(List.of(new Genre(1, "Комедия")))
            .withMpa(new Mpa(1, null));

    @BeforeEach
    public void add() {
        userDbStorage.createUser(user);
        userDbStorage.createUser(user);
        filmDbStorage.createFilm(film);
    }

    @Test
    public void testLikeFilm() {
        likedFilmsDbStorage.likeFilm(1, 1);
        Film film = filmDbStorage.getFilmById(1);
        Assertions.assertTrue(film.getLikes().contains(1));
    }

    @Test
    public void testDeleteLike() {
        likedFilmsDbStorage.likeFilm(1, 1);
        likedFilmsDbStorage.likeFilm(1, 2);
        likedFilmsDbStorage.deleteLike(1, 1);
        Film film = filmDbStorage.getFilmById(1);
        Assertions.assertTrue(film.getLikes().contains(2));
    }
}
