package com.pulkitBuilds.QuickLink.Controllers;

import com.pulkitBuilds.QuickLink.exception.ShortCodeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShortCodeNotFoundException.class)
    public ResponseEntity<Object> handleShortCodeNotFoundException(
            ShortCodeNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>(); // Prepare a structured error message
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage()); // The specific message from the exception
        body.put("path", request.getDescription(false).replace("uri=", "")); // Which URL caused this

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // Send back the structured message with 404 status
    }
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value()); // This will be 400 (Bad Request)
        body.put("error", "Validation Error");

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        body.put("errors", fieldErrors); // Add the map of field errors (e.g., "longUrl": "must not be blank")
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, headers, status);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(
            Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred. Please try again later."); // A generic message for the user
        body.put("path", request.getDescription(false).replace("uri=", ""));

        // IMPORTANT for you (the developer): Log the actual error for debugging
        // logger.error("Unhandled exception: ", ex); // You'd need to inject a Logger instance
        System.err.println("Unhandled exception: " + ex.getMessage()); // Simple console log for now

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);// Send back the structured message with 500 status
    }
}
