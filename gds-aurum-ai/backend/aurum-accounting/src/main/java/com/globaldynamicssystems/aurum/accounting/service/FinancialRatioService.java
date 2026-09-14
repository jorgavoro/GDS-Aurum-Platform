package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;

public interface FinancialRatioService {

    FinancialRatioReport calculate(Long chartOfAccountsId, Long fiscalPeriodId);
}
