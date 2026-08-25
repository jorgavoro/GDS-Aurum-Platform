package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriodType;

public interface FinancialComparisonPeriodService {

    FinancialComparisonPeriod resolveCurrentPeriod(Integer fiscalYear,
                                                   Integer periodNumber,
                                                   FinancialComparisonPeriodType type);

    FinancialComparisonPeriod resolvePreviousPeriod(Integer fiscalYear,
                                                    Integer periodNumber,
                                                    FinancialComparisonPeriodType type);
}
