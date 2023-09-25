package com.yandex.practicum.UserDbStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.UserDbStorage;
import com.yandex.practicum.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageTest {
    private final UserDbStorage userDbStorage;
    private User user = new User()
            .withEmail("test@test.ru")
            .withLogin("Login test")
            .withName("Name test")
            .withBirthday(LocalDate.of(1992, 10, 20));
    private User updatingUser = new User()
            .withId(1)
            .withEmail("UpdatingTest@test.ru")
            .withLogin("Updating login test")
            .withName("Updating name test")
            .withBirthday(LocalDate.of(1995, 11, 21));


    @BeforeEach
    public void add() {
        userDbStorage.createUser(user);
        userDbStorage.updateUser(updatingUser);
    }

    @Test
    public void testGetUserById() {
        Assertions.assertEquals(userDbStorage.getUserById(user.getId()), user);
    }

    @Test
    public void testGetAllUsers() {
        Assertions.assertFalse(userDbStorage.getAllUsers().isEmpty());
    }

    @Test
    public void testCreateUser() {
        User getUser = userDbStorage.getUserById(user.getId());
        Assertions.assertEquals("test@test.ru", getUser.getEmail());
        Assertions.assertEquals("Login test", getUser.getLogin());
        Assertions.assertEquals("Name test", getUser.getName());
        Assertions.assertEquals(LocalDate.of(1992, 10, 20), getUser.getBirthday());
    }

    @Test
    public void updateUser() {
        User getUser = userDbStorage.getUserById(updatingUser.getId());
        Assertions.assertEquals("UpdatingTest@test.ru", getUser.getEmail());
        Assertions.assertEquals("Updating login test", getUser.getLogin());
        Assertions.assertEquals("Updating name test", getUser.getName());
        Assertions.assertEquals(LocalDate.of(1995, 11, 21), getUser.getBirthday());
    }
}
