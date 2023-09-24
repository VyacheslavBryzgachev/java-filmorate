package com.yandex.practicum.dao;

import com.yandex.practicum.exceptions.UnknownIdException;
import com.yandex.practicum.model.User;
import com.yandex.practicum.storage.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate template;

    @Override
    public List<User> getAllUsers() {
        return template.query("SELECT * FROM USERS", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserById(int id) {
        List<Integer> friendIds = template.queryForList("SELECT f.friend_id FROM USERS u LEFT JOIN User_Friends f ON u.id = f.user_id WHERE u.id = ?",
                Integer.class, id);
        Set<Integer> friends = new HashSet<>(friendIds.isEmpty() ? friendIds : Collections.emptyList());
            User user = template.query("SELECT * FROM USERS WHERE id =?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                    .stream().findAny().orElseThrow(() -> new UnknownIdException("Пользователя с таким id=" + id + " не найдено"));
            user.setFriends(friends);
            return user;
    }

    @Override
    public User createUser(User user) {
        checkNameIsNotNull(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS(email, login, name, birthday)" +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return  statement;
        }, keyHolder);
           user.setId(keyHolder.getKey().intValue());
           return user;
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        template.update("UPDATE USERS SET email=?, login=?, name=?, birthday=? WHERE id =?", user.getEmail(),
                user.getLogin(), user.getName(), user.getBirthday(), user.getId());
            return user;
        }

    private void checkNameIsNotNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
