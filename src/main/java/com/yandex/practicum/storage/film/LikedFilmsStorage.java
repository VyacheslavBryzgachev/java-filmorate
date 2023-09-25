package com.yandex.practicum.storage.film;

public interface LikedFilmsStorage {
    void likeFilm(int filmId, int userId);

    void deleteLike(int filmId, int userId);

}
