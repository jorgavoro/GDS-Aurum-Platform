package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisReport;

public interface AccountFinancialAnalysisValidator {

    void validate(AccountFinancialAnalysisReport report);
}
