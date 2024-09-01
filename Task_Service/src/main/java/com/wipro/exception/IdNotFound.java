package com.wipro.exception;

public class IdNotFound extends RuntimeException {
    private String message;

    public IdNotFound(String message) {
        super(message);
        this.message = message;
    }
}
