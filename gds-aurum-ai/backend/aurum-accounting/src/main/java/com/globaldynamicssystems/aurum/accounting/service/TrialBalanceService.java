package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.TrialBalance;

public interface TrialBalanceService {

    TrialBalance generate(
        Long chartOfAccountsId,
        Long fiscalPeriodId
    );

    TrialBalance generate(
        Long chartOfAccountsId,
        Long fiscalPeriodId,
        boolean includeNonPostableAccounts
    );
    
    TrialBalance generateTrialBalance(
    		Long fiscalPeriodId
    );
}