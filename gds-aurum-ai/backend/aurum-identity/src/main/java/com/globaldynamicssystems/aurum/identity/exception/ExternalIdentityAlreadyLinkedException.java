package com.globaldynamicssystems.aurum.identity.exception;

public class ExternalIdentityAlreadyLinkedException extends RuntimeException {

    public ExternalIdentityAlreadyLinkedException() {
        super();
    }

    public ExternalIdentityAlreadyLinkedException(String message) {
        super(message);
    }

    public ExternalIdentityAlreadyLinkedException(String message, Throwable cause) {
        super(message, cause);
    }
}