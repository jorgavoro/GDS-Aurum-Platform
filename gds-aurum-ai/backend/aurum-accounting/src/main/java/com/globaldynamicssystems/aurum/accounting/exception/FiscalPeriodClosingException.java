package com.globaldynamicssystems.aurum.accounting.exception;

public class FiscalPeriodClosingException extends RuntimeException {

    public FiscalPeriodClosingException(String message) {
        super(message);
    }

    public FiscalPeriodClosingException(String message, Throwable cause) {
        super(message, cause);
    }
}