package com.he.engelund.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class ItemListNotFoundException extends EntityNotFoundException {

    private static final String message = "List with id '%s' not found";

    public ItemListNotFoundException() {
    }

    public ItemListNotFoundException(UUID id) {
        super(String.format(message, id));
    }
}
