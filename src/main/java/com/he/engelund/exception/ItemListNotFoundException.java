package com.he.engelund.exception;

import jakarta.persistence.EntityNotFoundException;

public class ItemListNotFoundException extends EntityNotFoundException {

    private static final String message = "List with id '%s' not found";

    public ItemListNotFoundException() {
    }

    public ItemListNotFoundException(String id) {
        super(String.format(message, id));
    }
}
