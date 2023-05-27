package com.he.engelund.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String message = "User with id '%s' not found";

    public UserNotFoundException() {
    }

    public UserNotFoundException(UUID id) {
        super(String.format(message, id));
    }
}
