package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceResult;

public interface OpeningBalanceService {

    OpeningBalanceResult generate(
            Long chartOfAccountsId,
            Integer sourceFiscalYear,
            Integer targetFiscalYear
    );

    OpeningBalanceResult post(
            Long chartOfAccountsId,
            Integer sourceFiscalYear,
            Integer targetFiscalYear
    );

    boolean exists(
            Long chartOfAccountsId,
            Integer targetFiscalYear
    );
}