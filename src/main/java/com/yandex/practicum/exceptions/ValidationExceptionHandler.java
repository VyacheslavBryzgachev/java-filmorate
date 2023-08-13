package com.yandex.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>((exception.getMessage()),  HttpStatus.BAD_REQUEST);
    }
}
