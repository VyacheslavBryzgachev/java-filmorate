package com.yandex.practicum.storage.user;

import com.yandex.practicum.model.User;

import java.util.List;


public interface UserStorage {

    List<User> getAllUsers();

    User getUserById(int id);

    User createUser(User user);

    User updateUser(User user);

    void addToFriends(int id, int friendId);

    void deleteFromFriends(int id, int friendId);

    List<User> getUserFriends(int id);

    List<User> getCommonFriends(int id, int otherId);
}
