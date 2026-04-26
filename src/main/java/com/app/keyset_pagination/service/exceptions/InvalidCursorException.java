package com.app.keyset_pagination.service.exceptions;

public class InvalidCursorException extends RuntimeException {

    public InvalidCursorException(String message) {
        super(message);
    }

    public InvalidCursorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
