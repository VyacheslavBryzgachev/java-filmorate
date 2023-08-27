package com.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResponseError {
    private final HttpStatus status;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDate.now().atStartOfDay();

    public ResponseError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
