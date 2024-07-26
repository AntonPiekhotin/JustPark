package com.parking.JustPark.exception;

public class UserByPrincipalNotFoundException extends RuntimeException {
    public UserByPrincipalNotFoundException(String message) {
        super(message);
    }
}
