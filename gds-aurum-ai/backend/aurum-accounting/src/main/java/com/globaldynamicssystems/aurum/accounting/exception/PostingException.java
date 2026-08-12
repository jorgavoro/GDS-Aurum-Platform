package com.globaldynamicssystems.aurum.accounting.exception;

public class PostingException extends RuntimeException {

    public PostingException(String message) {
        super(message);
    }

    public PostingException(String message, Throwable cause) {
        super(message, cause);
    }
}