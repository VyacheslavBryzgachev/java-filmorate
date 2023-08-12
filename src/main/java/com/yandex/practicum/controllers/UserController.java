package com.yandex.practicum.controllers;

import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    List<User> users = new ArrayList<>();
    int id = 1;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return users;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ExceptionHandler(ValidationException.class)
    public User createUser(@Valid @RequestBody User user) {
        user.setId(getNextId());
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.add(user);
        return user;
    }

    @RequestMapping(path = "/update", method = RequestMethod.PATCH)
    public User updateUser(@Valid @RequestBody User user) {
        int id = user.getId();
        for (User u : users) {
            if (u.getId() == id) {
                users.remove(u);
                users.add(user);
            }
        }
        return user;
    }

    private int getNextId() {
        return  id++;
    }
}
