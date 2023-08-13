package com.yandex.practicum.controllers;

import com.yandex.practicum.exceptions.ValidationException;
import com.yandex.practicum.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    Map<Integer, User> users = new HashMap<>();
    int id = 1;

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    @ExceptionHandler(ValidationException.class)
    public User createUser(@Valid @RequestBody User user) {
        user.setId(getNextId());
        checkNameIsNotBlank(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    @ExceptionHandler(ValidationException.class)
    public User updateUser(@Valid @RequestBody User user) {
        checkNameIsNotBlank(user);
       if (users.containsKey(user.getId())) {
           users.put(user.getId(), user);
       }
        return user;
    }

    private void checkNameIsNotBlank(User user) {
        if (user.getName().isBlank() || user.getName().isEmpty()) {
        user.setName(user.getLogin());
        }
    }

    private int getNextId() {
        return  id++;
    }
}
