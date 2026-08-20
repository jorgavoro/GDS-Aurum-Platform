package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountingPeriodOperation;

public interface AccountingOperationValidator {

    void validate(AccountingPeriodOperation operation);
}