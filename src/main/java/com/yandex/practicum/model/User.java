package com.yandex.practicum.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String login;

    private String name;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"yyyy.MM.dd"})
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();
}
