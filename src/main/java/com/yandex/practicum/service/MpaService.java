package com.yandex.practicum.service;

import com.yandex.practicum.dao.MpaDbStorage;
import com.yandex.practicum.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    public Mpa getMpaById(int id) {
       return mpaDbStorage.getMpaById(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAllMpa();
    }
}
