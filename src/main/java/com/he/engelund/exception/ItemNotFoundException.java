package com.he.engelund.exception;

import jakarta.persistence.EntityNotFoundException;

public class ItemNotFoundException extends EntityNotFoundException {

    private static final String message = "Item with id '%s' not found";

    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String id) {
        super(String.format(message, id));
    }

    public ItemNotFoundException(Exception cause) {
        super(cause);
    }

    public ItemNotFoundException(String id, Exception cause) {
        super(String.format(message, id), cause);
    }
}
