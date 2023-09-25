package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.mappers.FilmMapper;
import com.yandex.practicum.model.Film;
import com.yandex.practicum.model.Genre;
import com.yandex.practicum.storage.film.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate template;
    private final GenreDbStorage genreDbStorage;

    @Override
    public List<Film> getAllFilms() {
        String allFilmsSql = "SELECT f.*, m.NAME as mpa_name FROM FILMS f JOIN MPA M on f.MPA = M.id";
        List<Film> films = template.query(allFilmsSql, new FilmMapper());
        List<Integer> filmIds = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
       for (Film film : films) {
           filmIds.add(film.getId());
           String userSql = "SELECT user_id FROM FILM_LIKES WHERE film_id =?";
           SqlRowSet set = template.queryForRowSet(userSql, film.getId());
           while (set.next()) {
               film.getLikes().add(set.getInt("user_id"));
           }
           for (int id : filmIds) {
              genres = findFilmGenres(id);
           }
           film.setGenres(genres);
       }
       return films;
    }

    @Override
    public Film getFilmById(int id) {
        List<Genre> genres = findFilmGenres(id);
        String filmSql = "SELECT f.*, m.NAME as mpa_name FROM FILMS f JOIN MPA M on f.MPA = M.id WHERE f.ID = ?";
        Film film = template.query(filmSql, new Object[] {id}, new FilmMapper())
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Фильма с таким id=" + id + " не найдено"));
        film.setGenres(genres);
        String userSql = "SELECT user_id FROM FILM_LIKES WHERE film_id =?";
        SqlRowSet set = template.queryForRowSet(userSql, id);
        while (set.next()) {
            film.getLikes().add(set.getInt("user_id"));
        }
        return film;
    }

    @Override
    public Film createFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO FILMS(name, description, release_date, duration, mpa) " +
                "VALUES (?, ?, ?, ?, ?)";
       template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
            film.setId(keyHolder.getKey().intValue());

        for (Genre genre : film.getGenres()) {
            String genreSql = "INSERT INTO FILM_GENRES(film_id, genre_id) VALUES (?, ?)";
            template.update(genreSql, film.getId(), genre.getId());
        }
            return film;
    }

    @Override
    public Film updateFilm(Film film) {
        checkFilm(film.getId());
        String sql = "UPDATE FILMS SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id =?";
        template.update(sql,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        updateGenreByFilmToStorage(film);
        return getFilmById(film.getId());
    }

    private void checkFilm(int id) {
        template.query("SELECT f.*, m.NAME as mpa_name FROM FILMS f JOIN MPA M on f.MPA = M.id WHERE f.ID = ?", new Object[]{id}, new FilmMapper())
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Фильма с таким id=" + id + " не найдено"));
    }

     private void updateGenreByFilmToStorage(Film film) {
        String sqlForDeleteGenre = "DELETE FROM FILM_GENRES WHERE FILM_ID = ?";
        template.update(sqlForDeleteGenre, film.getId());

        if (!film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                String sqlForAddGenre = "INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM FILM_GENRES WHERE FILM_ID = ? AND GENRE_ID = ?)";
                template.update(sqlForAddGenre, film.getId(), genre.getId(), film.getId(), genre.getId());
            }
        }
    }

    private List<Genre> findFilmGenres(int id) {
        List<Integer> genreIds = template.query("SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID=?",
                new Object[]{id}, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("GENRE_ID");
                    }
                });

        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            Genre genre = genreDbStorage.getGenreById(genreId);
            genres.add(genre);
        }
        return genres;
    }
}
