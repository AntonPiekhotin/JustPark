package com.parking.JustPark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JustParkException extends RuntimeException {

    private final HttpStatus code;

    public JustParkException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
