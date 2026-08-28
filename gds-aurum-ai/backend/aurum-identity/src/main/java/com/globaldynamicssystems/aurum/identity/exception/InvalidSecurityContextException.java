package com.globaldynamicssystems.aurum.identity.exception;

public class InvalidSecurityContextException extends RuntimeException {

    public InvalidSecurityContextException() {
        super();
    }

    public InvalidSecurityContextException(String message) {
        super(message);
    }

    public InvalidSecurityContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
