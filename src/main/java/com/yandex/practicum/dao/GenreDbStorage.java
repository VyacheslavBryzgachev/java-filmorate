package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.mappers.GenreMapper;
import com.yandex.practicum.model.Genre;
import com.yandex.practicum.storage.genre.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate template;

    public Genre getGenreById(int id) {
        return template.query("SELECT * FROM GENRE WHERE id =?", new Object[]{id}, new GenreMapper())
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Жанра с такими id=" + id + " не найдено"));
    }

    public List<Genre> getAllGenre() {
        return template.query("SELECT * FROM GENRE GROUP BY id", new GenreMapper());
    }
}
