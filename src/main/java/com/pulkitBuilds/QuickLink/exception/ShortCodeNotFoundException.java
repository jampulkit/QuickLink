package com.pulkitBuilds.QuickLink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String message) {
        super(message);
    }

    public ShortCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
