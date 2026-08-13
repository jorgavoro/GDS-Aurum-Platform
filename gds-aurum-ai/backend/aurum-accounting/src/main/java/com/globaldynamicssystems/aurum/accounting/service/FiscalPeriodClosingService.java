package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;

public interface FiscalPeriodClosingService {

    FiscalPeriod close(Long fiscalPeriodId);

    boolean canClose(Long fiscalPeriodId);
}