package com.example.errors;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundException extends ResponseStatusException {
    public ItemNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
