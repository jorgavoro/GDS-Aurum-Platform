package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriodResult;
import com.globaldynamicssystems.aurum.accounting.model.FinancialPeriodComparisonRequest;

public interface FinancialPeriodComparisonEngine {

    FinancialComparisonPeriodResult compare(FinancialPeriodComparisonRequest request);
}
