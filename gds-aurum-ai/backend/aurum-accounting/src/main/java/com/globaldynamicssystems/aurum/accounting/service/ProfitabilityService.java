package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;

import java.util.List;

public interface ProfitabilityService {

    ProfitabilityReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                 AnalyticalDimensionType dimensionType);

    ProfitabilityReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                 AnalyticalDimensionType dimensionType, List<Long> dimensionIds);
    

}
