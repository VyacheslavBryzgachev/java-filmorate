package com.yandex.practicum.storage.genre;

import com.yandex.practicum.model.Genre;

import java.util.List;

public interface GenreStorage {
    Genre getGenreById(int id);

    List<Genre> getAllGenre();
}
