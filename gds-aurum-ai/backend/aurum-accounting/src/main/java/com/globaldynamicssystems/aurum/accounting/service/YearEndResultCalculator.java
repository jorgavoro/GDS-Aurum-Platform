package com.globaldynamicssystems.aurum.accounting.service;

import java.math.BigDecimal;

public interface YearEndResultCalculator {

    BigDecimal calculateNetIncome(
            Long chartOfAccountsId,
            Integer fiscalYear
    );

    BigDecimal calculateRevenue(
            Long chartOfAccountsId,
            Integer fiscalYear
    );

    BigDecimal calculateExpense(
            Long chartOfAccountsId,
            Integer fiscalYear
    );
}