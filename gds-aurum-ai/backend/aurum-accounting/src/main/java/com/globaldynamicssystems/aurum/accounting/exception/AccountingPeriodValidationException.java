package com.globaldynamicssystems.aurum.accounting.exception;

public class AccountingPeriodValidationException extends RuntimeException {

    public AccountingPeriodValidationException(String message) {
        super(message);
    }

    public AccountingPeriodValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}