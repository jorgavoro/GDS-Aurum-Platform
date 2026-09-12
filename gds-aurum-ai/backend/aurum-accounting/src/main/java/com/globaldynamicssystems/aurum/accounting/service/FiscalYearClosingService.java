package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FiscalYearClosingResult;

public interface FiscalYearClosingService {

    FiscalYearClosingResult closeYear(
            Long chartOfAccountsId,
            Integer fiscalYear,
            Long retainedEarningsAccountId
    );

    boolean canCloseYear(
            Long chartOfAccountsId,
            Integer fiscalYear
    );
}