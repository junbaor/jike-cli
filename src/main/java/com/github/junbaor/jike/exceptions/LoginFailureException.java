package com.github.junbaor.jike.exceptions;

public class LoginFailureException extends Exception {

    public LoginFailureException() {
        super();
    }

    public LoginFailureException(String message) {
        super(message);
    }

    public LoginFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailureException(Throwable cause) {
        super(cause);
    }

}
