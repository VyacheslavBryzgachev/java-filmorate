package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.Mpa;
import com.yandex.practicum.storage.mpa.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate template;

    public Mpa getMpaById(int id) {
       return template.query("SELECT * FROM MPA WHERE id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Mpa.class))
               .stream().findAny().orElseThrow(() -> new UnknownIdException("Рейтинга с таким id=" + id + " не найдено"));
    }

    public List<Mpa> getAllMpa() {
        return template.query("SELECT * FROM MPA ORDER BY id", new BeanPropertyRowMapper<>(Mpa.class));
    }
}
