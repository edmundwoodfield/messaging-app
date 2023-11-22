package com.example.errors;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestException extends ResponseStatusException {
    public InvalidRequestException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
