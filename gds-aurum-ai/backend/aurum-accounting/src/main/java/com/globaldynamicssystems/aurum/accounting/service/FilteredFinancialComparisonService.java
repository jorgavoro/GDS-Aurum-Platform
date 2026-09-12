package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;

public interface FilteredFinancialComparisonService {

    FinancialComparisonReport compare(FinancialComparisonFilter filter);
}
