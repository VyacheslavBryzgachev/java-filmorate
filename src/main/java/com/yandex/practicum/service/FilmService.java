package com.yandex.practicum.service;

import com.yandex.practicum.storage.film.InMemoryFilmStorage;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryUserStorage inMemoryUserStorage, InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
}
