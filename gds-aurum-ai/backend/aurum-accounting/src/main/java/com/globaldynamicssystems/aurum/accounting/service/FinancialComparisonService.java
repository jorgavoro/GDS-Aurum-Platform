package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;

import java.util.List;

public interface FinancialComparisonService {

    FinancialComparisonReport compare(Long chartOfAccountsId,
                                      Long currentFiscalPeriodId,
                                      Long previousFiscalPeriodId,
                                      AnalyticalDimensionType dimensionType);

    FinancialComparisonReport compare(Long chartOfAccountsId,
                                      Long currentFiscalPeriodId,
                                      Long previousFiscalPeriodId,
                                      AnalyticalDimensionType dimensionType,
                                      List<Long> dimensionIds);

    FinancialComparisonReport compare(Long chartOfAccountsId,
                                      List<Long> currentFiscalPeriodIds,
                                      List<Long> previousFiscalPeriodIds,
                                      String currentPeriodLabel,
                                      String previousPeriodLabel,
                                      AnalyticalDimensionType dimensionType,
                                      List<Long> dimensionIds);
}
