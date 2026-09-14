package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriod;

import java.util.List;

public interface FinancialPeriodAggregationService {

    List<Long> resolveFiscalPeriodIds(Long chartOfAccountsId, FinancialComparisonPeriod period);
}
