package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;

public interface FinancialRatioValidator {

    void validate(FinancialRatioReport report);
}
