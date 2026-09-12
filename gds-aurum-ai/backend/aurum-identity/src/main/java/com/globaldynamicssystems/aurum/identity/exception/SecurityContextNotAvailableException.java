package com.globaldynamicssystems.aurum.identity.exception;

public class SecurityContextNotAvailableException extends RuntimeException {
    public SecurityContextNotAvailableException() {
        super("Security context is not available on the current thread.");
    }

    public SecurityContextNotAvailableException(String message) {
        super(message);
    }

    public SecurityContextNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
