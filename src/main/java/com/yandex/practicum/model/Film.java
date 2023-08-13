package com.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;

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


    @JsonAnySetter
    public void setAdditionalProperty(String key) {
        if (!Arrays.asList("id", "name", "description", "releaseDate", "duration").contains(key)) {
            throw new IllegalArgumentException("Unknown property: " + key);
        }
    }
}
