package com.yandex.practicum.controllers;

import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.user.InMemoryUserStorage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Data
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage) {
    this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable int id) {
        return inMemoryUserStorage.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
       return inMemoryUserStorage.updateUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addToFriends(@Valid @PathVariable int id, @PathVariable int friendId) {
     inMemoryUserStorage.addToFriends(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFromFriends(@Valid @PathVariable int id, @PathVariable int friendId) {
        inMemoryUserStorage.deleteFromFriends(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getUserFriends(@Valid @PathVariable int id) {
        return inMemoryUserStorage.getUserFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@Valid @PathVariable int id, @PathVariable int otherId) {
        return inMemoryUserStorage.getCommonFriends(id, otherId);
    }
}
