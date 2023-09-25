package com.yandex.practicum.storage.user;

import com.yandex.practicum.model.User;

import java.util.List;

public interface UserFriendsStorage {
    List<User> getUserFriends(int userId);

    void  addToFriends(int userId, int friendId);

    void deleteFromFriends(int userId, int friendId);

    List<User> getCommonFriends(int id, int otherId);
}
