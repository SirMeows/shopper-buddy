package com.he.engelund.exception;

//TODO: Good to have a custom UserUnauthorisedException
public class UserNotListOwnerException extends RuntimeException {

    private static final String message = "User with id '%s' is not the list owner";

    public UserNotListOwnerException() {

    }

    public UserNotListOwnerException(Throwable cause) {
        super(cause);
    }

    public UserNotListOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotListOwnerException(String id) {
        super(String.format(message, id));

    }
}
