package com.yandex.practicum.storage.user;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    Map<Integer, User> users = new HashMap<>();
    int id = 1;

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int id) {
        User user = users.get(id);
        if(user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        return users.get(id);
    }

    @Override
    public User createUser(User user) {
        user.setId(getNextId());
        checkNameIsNotNull(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        checkNameIsNotNull(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
        else {
            throw new UnknownIdException("Пользователя с таким id= " + user.getId() + " не найдено");
        }
        return user;
    }

    @Override
    public void addToFriends(int id, int friendId) {
        User user = users.get(id);
        if(user != null) {
            user.getFriends().add(friendId);
        }
        else {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        User otherUser = users.get(friendId);
        if(otherUser != null) {
            otherUser.getFriends().add(id);
        }
        else {
            throw new UnknownIdException("Пользователя с таким id=" + friendId + " не найдено");
        }
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        User user = users.get(id);
        if(user != null) {
            user.getFriends().remove(friendId);
        }
        else {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        User otherUser = users.get(friendId);
        if(otherUser != null) {
            otherUser.getFriends().remove(id);
        }
        else {
            throw new UnknownIdException("Пользователя с таким id=" + friendId + " не найдено");
        }
    }

    @Override
    public List<User> getUserFriends(int id) {
        User user = users.get(id);
        Set<Integer> friendsIds = user.getFriends();
        List<User> userFriends = new ArrayList<>();
        for (int friend : friendsIds) {
            if(users.containsKey(friend)) {
                User u = users.get(friend);
                userFriends.add(u);
            }
        }
        return userFriends;
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        User user = users.get(id);
        User otherUser = users.get(otherId);
        Set<Integer> userFriendIds = user.getFriends();
        Set<Integer> otherFriendIds = otherUser.getFriends();
        List<User> commonFriends = new ArrayList<>();
        for(int userFriend : userFriendIds) {
            if(otherFriendIds.contains(userFriend)) {
                commonFriends.add(users.get(userFriend));
            }
        }
        return commonFriends;
    }

    private void checkNameIsNotNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private int getNextId() {
        return  id++;
    }
}
