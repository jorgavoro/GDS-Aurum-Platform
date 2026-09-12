package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.IncomeStatement;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementService;
import com.globaldynamicssystems.aurum.accounting.service.YearEndResultCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class DefaultYearEndResultCalculator implements YearEndResultCalculator {

    private final FinancialStatementService financialStatementService;

    public DefaultYearEndResultCalculator(FinancialStatementService financialStatementService) {
        this.financialStatementService = Objects.requireNonNull(financialStatementService, "financialStatementService must not be null");
    }

    @Override
    public BigDecimal calculateRevenue(Long chartOfAccountsId, Integer fiscalYear) {
        IncomeStatement statement = getIncomeStatement(chartOfAccountsId, fiscalYear);
        if (statement == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal revenue = statement.getTotalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateExpense(Long chartOfAccountsId, Integer fiscalYear) {
        IncomeStatement statement = getIncomeStatement(chartOfAccountsId, fiscalYear);
        if (statement == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal expense = statement.getTotalExpense();
        return expense != null ? expense : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateNetIncome(Long chartOfAccountsId, Integer fiscalYear) {
        BigDecimal revenue = calculateRevenue(chartOfAccountsId, fiscalYear);
        BigDecimal expense = calculateExpense(chartOfAccountsId, fiscalYear);
        return revenue.subtract(expense);
    }

    private IncomeStatement getIncomeStatement(Long chartOfAccountsId, Integer fiscalYear) {
        if (chartOfAccountsId == null || fiscalYear == null) {
            return null;
        }

        Long fiscalYearLong = fiscalYear.longValue();

        try {
            return financialStatementService.generateIncomeStatement(chartOfAccountsId, fiscalYearLong);
        } catch (Exception e) {
            return null;
        }
    }
}