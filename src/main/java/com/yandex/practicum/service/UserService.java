package com.yandex.practicum.service;

import com.yandex.practicum.dao.UserDbStorage;
import com.yandex.practicum.dao.UserFriendsDbStorage;
import com.yandex.practicum.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

private final UserDbStorage userDbStorage;
private final UserFriendsDbStorage userFriendsDbStorage;


    public List<User> getAllUsers() {
        return userDbStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return userDbStorage.getUserById(id);
    }

    public User createUser(User user) {
        return userDbStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userDbStorage.updateUser(user);
    }

    public List<User> getUserFriends(int id) {
        return userFriendsDbStorage.getUserFriends(id);
    }

    public List<User> getCommonFriends(int id, int otherId) {
       return userFriendsDbStorage.getCommonFriends(id, otherId);
    }

    public void addToFriends(int id, int friendId) {
        userFriendsDbStorage.addToFriends(id, friendId);
    }

    public void deleteFromFriends(int id, int friendId) {
        userFriendsDbStorage.deleteFromFriends(id, friendId);
    }
}
