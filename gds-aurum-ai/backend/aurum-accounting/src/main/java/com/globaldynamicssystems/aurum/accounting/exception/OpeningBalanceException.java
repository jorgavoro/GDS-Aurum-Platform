package com.globaldynamicssystems.aurum.accounting.exception;

public class OpeningBalanceException extends RuntimeException {

    public OpeningBalanceException(String message) {
        super(message);
    }

    public OpeningBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}