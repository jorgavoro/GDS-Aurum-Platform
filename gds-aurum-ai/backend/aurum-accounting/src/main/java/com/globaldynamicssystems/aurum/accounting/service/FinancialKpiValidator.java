package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;

public interface FinancialKpiValidator {

    void validate(FinancialKpiReport report);
}