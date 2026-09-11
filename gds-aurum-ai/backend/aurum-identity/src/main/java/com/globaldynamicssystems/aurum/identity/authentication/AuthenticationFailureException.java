package com.globaldynamicssystems.aurum.identity.authentication;

public class AuthenticationFailureException extends RuntimeException {

    public AuthenticationFailureException() {
        super();
    }

    public AuthenticationFailureException(String message) {
        super(message);
    }

    public AuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
