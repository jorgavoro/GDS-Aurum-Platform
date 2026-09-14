package com.globaldynamicssystems.aurum.accounting.service;

public interface FiscalYearClosingValidator {

    void validate(
            Long chartOfAccountsId,
            Integer fiscalYear,
            Long retainedEarningsAccountId
    );
}