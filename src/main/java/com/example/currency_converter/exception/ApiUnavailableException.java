package com.example.currency_converter.exception;

public class ApiUnavailableException extends RuntimeException {
    public ApiUnavailableException(String message) {
        super(message);
    }
}
