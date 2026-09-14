package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.BalanceSheet;
import com.globaldynamicssystems.aurum.accounting.model.IncomeStatement;

public interface FinancialStatementValidator {

    void validateBalanceSheet(BalanceSheet balanceSheet);

    void validateIncomeStatement(IncomeStatement incomeStatement);
}