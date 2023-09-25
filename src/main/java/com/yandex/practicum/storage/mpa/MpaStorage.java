package com.yandex.practicum.storage.mpa;

import com.yandex.practicum.model.Mpa;

import java.util.List;

public interface MpaStorage {
    Mpa getMpaById(int id);

    List<Mpa> getAllMpa();
}
