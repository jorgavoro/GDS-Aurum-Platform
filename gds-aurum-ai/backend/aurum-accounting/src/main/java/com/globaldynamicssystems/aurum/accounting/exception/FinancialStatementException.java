package com.globaldynamicssystems.aurum.accounting.exception;

public class FinancialStatementException extends RuntimeException {

    public FinancialStatementException(String message) {
        super(message);
    }

    public FinancialStatementException(String message, Throwable cause) {
        super(message, cause);
    }
}