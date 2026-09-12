package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;

public interface FilteredFinancialKpiService {

    FinancialKpiReport calculate(
            FinancialKpiFilter filter
    );
}