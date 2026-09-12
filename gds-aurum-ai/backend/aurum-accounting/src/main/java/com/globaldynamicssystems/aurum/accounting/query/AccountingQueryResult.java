package com.globaldynamicssystems.aurum.accounting.query;

public interface AccountingQueryResult {
    AccountingQueryType getQueryType();
    Boolean isValid();
}