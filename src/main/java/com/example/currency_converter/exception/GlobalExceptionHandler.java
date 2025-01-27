package com.example.currency_converter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleApiUnavailableException(ApiUnavailableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "External API is unavailable");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCurrencyException(InvalidCurrencyException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid Currency Code");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
