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
<<<<<<< HEAD
=======
    
    TrialBalance generateTrialBalance(
    		Long fiscalPeriodId
    );
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
}