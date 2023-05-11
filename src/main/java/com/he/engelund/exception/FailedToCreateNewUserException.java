package com.he.engelund.exception;

public class FailedToCreateNewUserException extends RuntimeException {

    public FailedToCreateNewUserException() {
    }

    public FailedToCreateNewUserException(String message) {
        super(message);
    }

    public FailedToCreateNewUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToCreateNewUserException(Throwable cause) {
        super(cause);
    }
}
