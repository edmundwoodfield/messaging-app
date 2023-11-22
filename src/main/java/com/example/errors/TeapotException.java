package com.example.errors;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class TeapotException extends ResponseStatusException {
    public TeapotException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
