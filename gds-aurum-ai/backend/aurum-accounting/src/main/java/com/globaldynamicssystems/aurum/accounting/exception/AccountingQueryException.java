package com.globaldynamicssystems.aurum.accounting.exception;

public class AccountingQueryException extends RuntimeException {

    public AccountingQueryException() {
        super();
    }

    public AccountingQueryException(String message) {
        super(message);
    }

    public AccountingQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}