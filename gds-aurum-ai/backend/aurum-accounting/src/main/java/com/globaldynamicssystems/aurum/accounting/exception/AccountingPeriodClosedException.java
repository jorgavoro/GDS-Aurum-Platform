package com.globaldynamicssystems.aurum.accounting.exception;

public class AccountingPeriodClosedException extends RuntimeException {

    public AccountingPeriodClosedException(String message) {
        super(message);
    }

    public AccountingPeriodClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}