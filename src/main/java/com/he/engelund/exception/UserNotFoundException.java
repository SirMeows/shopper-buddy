package com.he.engelund.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String message = "User with username '%s' not found";

    public UserNotFoundException() {
    }

    public UserNotFoundException(String username) {
        super(String.format(message, username));
    }
}
