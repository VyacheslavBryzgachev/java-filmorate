package com.yandex.practicum.genreStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.GenreDbStorage;
import com.yandex.practicum.model.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreStorageTest {
     private final GenreDbStorage genreDbStorage;

        @Test
        public void testGetAllGenres() {
            Assert.noNullElements(genreDbStorage.getAllGenre().toArray());
        }

        @Test
        public void testGetGenreById() {
            Genre genre = genreDbStorage.getGenreById(1);
            Assertions.assertEquals(1, genre.getId());
            Assertions.assertEquals("Комедия", genre.getName());
        }
}
