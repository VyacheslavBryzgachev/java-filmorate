package com.yandex.practicum.exceptions;

public class UnknownIdException extends RuntimeException {

    public UnknownIdException(String message) {
        super(message);
    }
}
