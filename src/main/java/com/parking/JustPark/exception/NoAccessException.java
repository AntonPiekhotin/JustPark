package com.parking.JustPark.exception;

public class NoAccessException extends RuntimeException {
    public NoAccessException() {
        super("You don`t have access to this resource");
    }
}
