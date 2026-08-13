package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodClosingResult;

public interface FiscalPeriodClosingCheckService {

    FiscalPeriodClosingResult check(Long fiscalPeriodId);
}