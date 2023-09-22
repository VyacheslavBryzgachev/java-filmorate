package com.yandex.practicum.service;

import com.yandex.practicum.dao.GenreDbStorage;
import com.yandex.practicum.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public List<Genre> getAllGenre() {
       return genreDbStorage.getAllGenre();
    }

    public Genre getGenreById(int id) {
        return genreDbStorage.getGenreById(id);
    }
}
