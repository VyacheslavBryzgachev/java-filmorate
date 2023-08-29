package com.yandex.practicum.storage.user;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
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
        if (user == null) {
            throw new UnknownIdException("Пользователя с таким id=" + id + " не найдено");
        }
        return user;
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
        } else {
            throw new UnknownIdException("Пользователя с таким id= " + user.getId() + " не найдено");
        }
        return user;
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
