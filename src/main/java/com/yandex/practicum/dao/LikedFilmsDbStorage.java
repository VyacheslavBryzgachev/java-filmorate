package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.mappers.FilmMapper;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.film.LikedFilmsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikedFilmsDbStorage implements LikedFilmsStorage {
    private final JdbcTemplate template;

    public void likeFilm(int filmId, int userId) {
        checkUser(userId);
        checkFilm(filmId);
        template.update("INSERT INTO FILM_LIKES(user_id, film_id) VALUES(?, ?)", userId, filmId);
    }

    public void deleteLike(int filmId, int userId) {
        checkUser(userId);
        checkFilm(filmId);
        template.update("DELETE FROM FILM_LIKES WHERE film_id =? and user_id=?", filmId, userId);
    }

    private void checkUser(int id) {
        template.query("SELECT * FROM USERS WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Пользователя с таким id=" + id + " не найдено"));
    }

    private void checkFilm(int id) {
        template.query("SELECT * FROM FILMS WHERE id=?", new Object[]{id}, new FilmMapper())
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Фильма с таким id=" + id + " не найдено"));
    }
}
