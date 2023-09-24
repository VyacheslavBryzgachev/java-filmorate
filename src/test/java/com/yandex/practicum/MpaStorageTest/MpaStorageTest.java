package com.yandex.practicum.MpaStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.MpaDbStorage;
import com.yandex.practicum.model.Mpa;
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
public class MpaStorageTest {

    private final MpaDbStorage mpaDbStorage;

    @Test
    public void testGetAllMpa() {
        Assert.noNullElements(mpaDbStorage.getAllMpa().toArray());
    }

    @Test
    public void testGetMpaById() {
        Mpa mpa = mpaDbStorage.getMpaById(1);
        Assertions.assertEquals(1, mpa.getId());
        Assertions.assertEquals("G", mpa.getName());
    }
}
