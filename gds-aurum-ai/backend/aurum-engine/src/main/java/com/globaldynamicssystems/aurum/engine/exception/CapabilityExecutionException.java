package com.globaldynamicssystems.aurum.engine.exception;

public class CapabilityExecutionException extends RuntimeException {

    public CapabilityExecutionException() {
        super();
    }

    public CapabilityExecutionException(String message) {
        super(message);
    }

    public CapabilityExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}