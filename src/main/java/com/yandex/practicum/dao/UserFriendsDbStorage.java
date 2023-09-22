package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.user.UserFriendsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserFriendsDbStorage implements UserFriendsStorage {
    private final JdbcTemplate template;
    private final UserDbStorage userDbStorage;

    public List<User> getUserFriends(int userId) {
        checkUser(userId);
        List<User> users = new ArrayList<>();
        List<Integer> friends = new ArrayList<>();
        SqlRowSet rowSet = template.queryForRowSet("SELECT * FROM USER_FRIENDS WHERE user_id =?", userId);
        while (rowSet.next()) {
            friends.add(rowSet.getInt("friend_id"));
        }
        for (int friend : friends) {
           User user = userDbStorage.getUserById(friend);
           users.add(user);
        }
        return users;
    }

    public void  addToFriends(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        template.update("INSERT INTO USER_FRIENDS(user_id, friend_id) VALUES (?, ?)", userId, friendId);
    }

    public void deleteFromFriends(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        template.update("DELETE USER_FRIENDS WHERE user_id =? and friend_id=?", userId, friendId);
    }

    public List<User> getCommonFriends(int id, int otherId) {
        List<User> commonFriends = new ArrayList<>();
        Set<Integer> userFriendsIds = new HashSet<>();
        Set<Integer> otherFriendIds = new HashSet<>();
        SqlRowSet row = template.queryForRowSet("SELECT * FROM USER_FRIENDS WHERE user_id =?",id);
        while (row.next()) {
            userFriendsIds.add(row.getInt("friend_id"));
        }
        SqlRowSet set = template.queryForRowSet("SELECT * FROM USER_FRIENDS WHERE user_id =?",otherId);
        while (set.next()) {
            otherFriendIds.add(set.getInt("friend_id"));
        }
        for (int userFriend : userFriendsIds) {
            if (otherFriendIds.contains(userFriend)) {
                commonFriends.add(userDbStorage.getUserById(userFriend));
            }
        }
        return commonFriends;
    }

    private void checkUser(int id) {
        template.query("SELECT * FROM USERS WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElseThrow(() -> new UnknownIdException("Пользователя с таким id=" + id + " не найдено"));
    }
}
