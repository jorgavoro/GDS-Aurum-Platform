package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;

public interface FinancialKpiService extends FilteredFinancialKpiService {

    FinancialKpiReport calculate(
            Long chartOfAccountsId,
            Long fiscalPeriodId
    );

    @Override
    FinancialKpiReport calculate(
            FinancialKpiFilter filter
    );
}