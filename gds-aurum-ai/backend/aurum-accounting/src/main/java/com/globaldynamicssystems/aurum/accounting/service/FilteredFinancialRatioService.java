package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;

public interface FilteredFinancialRatioService {

    FinancialRatioReport calculate(FinancialRatioFilter filter);
}
