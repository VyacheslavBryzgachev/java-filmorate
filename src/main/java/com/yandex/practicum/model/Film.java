package com.yandex.practicum.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
public class Film implements Comparable<Film>{

    private int id;

    @NotBlank
    @NotNull
    private String name;

    @Size(max = 200)
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"yyyy.MM.dd"})
    private LocalDate releaseDate;

    @Positive
    private int duration;

    private int likes;

    @Override
    public int compareTo(Film o) {
        if (this.likes == o.likes) {
            return 0;
        } else if (this.likes < o.likes) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
