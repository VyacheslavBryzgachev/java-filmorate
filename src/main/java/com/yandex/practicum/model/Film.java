package com.yandex.practicum.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

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

    private Set<Integer> likes = new HashSet<>();

    public static final Comparator<Film> COMPARE_BY_LIKES = new Comparator<Film>() {
        @Override
        public int compare(Film o1, Film o2) {
            return o1.getLikes().size() - o2.getLikes().size();
        }
    };
}
