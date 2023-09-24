package com.yandex.practicum.UserFriendsStorageTest;

import com.yandex.practicum.FilmorateApplication;
import com.yandex.practicum.dao.UserDbStorage;
import com.yandex.practicum.dao.UserFriendsDbStorage;
import com.yandex.practicum.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserFriendsStorageTest {
    private final UserFriendsDbStorage userFriendsDbStorage;
    private final UserDbStorage userDbStorage;

    private User user = new User()
            .withEmail("test@test.ru")
            .withLogin("Login test")
            .withName("Name test")
            .withBirthday(LocalDate.of(1992, 10, 20));
    private User friend = new User()
            .withEmail("FriendTest@test.ru")
            .withLogin("Friend login test")
            .withName("Friend name test")
            .withBirthday(LocalDate.of(1995, 11, 21));


    @BeforeEach
    public void add() {
        userDbStorage.createUser(user);
        userDbStorage.createUser(friend);
        userDbStorage.createUser(friend);
    }

    @Test
    public void testAddToFriends() {
        userFriendsDbStorage.addToFriends(1, 2);
        List<User> userFriends = userFriendsDbStorage.getUserFriends(1);
        Assertions.assertEquals(List.of(userDbStorage.getUserById(2), userDbStorage.getUserById(3), userDbStorage.getUserById(2)), userFriends);
    }

    @Test
    public void testDeleteFromFriends() {
        userFriendsDbStorage.deleteFromFriends(1, 2);
        List<User> userFriends = userFriendsDbStorage.getUserFriends(1);
        Assertions.assertEquals(List.of(userDbStorage.getUserById(3)), userFriends);
    }

    @Test
    public void testGetCommonFriends() {
        userFriendsDbStorage.addToFriends(1, 2);
        userFriendsDbStorage.addToFriends(1, 3);
        userFriendsDbStorage.addToFriends(2, 3);
        List<User> commonFriends = userFriendsDbStorage.getCommonFriends(1, 3);
        Assertions.assertEquals(Collections.emptyList(), commonFriends);
    }
}
