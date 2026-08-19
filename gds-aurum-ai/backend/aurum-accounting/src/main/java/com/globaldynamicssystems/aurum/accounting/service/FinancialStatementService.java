package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.BalanceSheet;
import com.globaldynamicssystems.aurum.accounting.model.IncomeStatement;

public interface FinancialStatementService {

    BalanceSheet generateBalanceSheet(
            Long chartOfAccountsId,
            Long fiscalPeriodId
    );

    IncomeStatement generateIncomeStatement(
            Long chartOfAccountsId,
            Long fiscalPeriodId
    );
}