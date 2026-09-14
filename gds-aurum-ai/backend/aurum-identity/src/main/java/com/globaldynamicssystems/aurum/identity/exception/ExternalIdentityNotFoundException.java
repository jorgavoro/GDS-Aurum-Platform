package com.globaldynamicssystems.aurum.identity.exception;

public class ExternalIdentityNotFoundException extends RuntimeException {

    public ExternalIdentityNotFoundException() {
        super();
    }

    public ExternalIdentityNotFoundException(String message) {
        super(message);
    }

    public ExternalIdentityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}