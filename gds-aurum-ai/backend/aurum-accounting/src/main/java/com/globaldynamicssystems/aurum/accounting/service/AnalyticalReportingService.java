package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;

import java.util.List;

public interface AnalyticalReportingService {

    AnalyticalReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                              AnalyticalDimensionType dimensionType);

    AnalyticalReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                              AnalyticalDimensionType dimensionType, List<Long> dimensionIds);
}
