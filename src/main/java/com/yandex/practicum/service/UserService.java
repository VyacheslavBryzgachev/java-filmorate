package com.yandex.practicum.service;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

private final InMemoryUserStorage inMemoryUserStorage;


    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return inMemoryUserStorage.getUserById(id);
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public List<User> getUserFriends(int id) {
        User user = inMemoryUserStorage.getUserById(id);
        Set<Integer> friendsIds = user.getFriends();
        List<User> userFriends = new ArrayList<>();
        for (int friend : friendsIds) {
            if (inMemoryUserStorage.getUsers().containsKey(friend)) {
                User u = inMemoryUserStorage.getUserById(friend);
                userFriends.add(u);
            }
        }
        return userFriends;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        User user = inMemoryUserStorage.getUserById(id);
        User otherUser = inMemoryUserStorage.getUserById(otherId);
        Set<Integer> userFriendIds = user.getFriends();
        Set<Integer> otherFriendIds = otherUser.getFriends();
        List<User> commonFriends = new ArrayList<>();
        for (int userFriend : userFriendIds) {
            if (otherFriendIds.contains(userFriend)) {
                commonFriends.add(inMemoryUserStorage.getUserById(userFriend));
            }
        }
        return commonFriends;
    }

    public void addToFriends(int id, int friendId) {
        User user = inMemoryUserStorage.getUserById(id);
        if (user != null) {
            user.getFriends().add(friendId);
        } else {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        User otherUser = inMemoryUserStorage.getUserById(friendId);
        if (otherUser != null) {
            otherUser.getFriends().add(id);
        } else {
            throw new UnknownIdException("Пользователя с таким id=" + friendId + " не найдено");
        }
    }

    public void deleteFromFriends(int id, int friendId) {
        User user = inMemoryUserStorage.getUserById(id);
        if (user != null) {
            user.getFriends().remove(friendId);
        } else {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        User otherUser = inMemoryUserStorage.getUserById(friendId);
        if (otherUser != null) {
            otherUser.getFriends().remove(id);
        } else {
            throw new UnknownIdException("Пользователя с таким id=" + friendId + " не найдено");
        }
    }
}
