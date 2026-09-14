package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowFilter;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;

public interface CashFlowService {

    CashFlowReport generate(Long chartOfAccountsId, Long fiscalPeriodId);

    CashFlowReport generate(CashFlowFilter filter);
}
