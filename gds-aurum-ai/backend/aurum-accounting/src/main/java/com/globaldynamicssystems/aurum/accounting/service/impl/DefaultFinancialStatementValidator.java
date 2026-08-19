package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.BalanceSheet;
import com.globaldynamicssystems.aurum.accounting.model.IncomeStatement;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultFinancialStatementValidator implements FinancialStatementValidator {

    public DefaultFinancialStatementValidator() {
    }

    @Override
    public void validateBalanceSheet(BalanceSheet balanceSheet) {
        if (balanceSheet == null) {
            throw new IllegalArgumentException("BalanceSheet cannot be null");
        }
        if (balanceSheet.getTotalAssets() == null) {
            throw new IllegalArgumentException("totalAssets cannot be null");
        }
        if (balanceSheet.getTotalLiabilities() == null) {
            throw new IllegalArgumentException("totalLiabilities cannot be null");
        }
        if (balanceSheet.getTotalEquity() == null) {
            throw new IllegalArgumentException("totalEquity cannot be null");
        }
        if (balanceSheet.getLiabilitiesAndEquity() == null) {
            throw new IllegalArgumentException("liabilitiesAndEquity cannot be null");
        }

        if (balanceSheet.getTotalAssets().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalAssets cannot be negative");
        }
        if (balanceSheet.getTotalLiabilities().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalLiabilities cannot be negative");
        }
        if (balanceSheet.getTotalEquity().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalEquity cannot be negative");
        }

        if (balanceSheet.getTotalAssets().compareTo(balanceSheet.getLiabilitiesAndEquity()) != 0) {
            throw new IllegalArgumentException("Balance sheet equation failed: totalAssets must equal totalLiabilities + totalEquity");
        }
    }

    @Override
    public void validateIncomeStatement(IncomeStatement incomeStatement) {
        if (incomeStatement == null) {
            throw new IllegalArgumentException("IncomeStatement cannot be null");
        }
        if (incomeStatement.getTotalRevenue() == null) {
            throw new IllegalArgumentException("totalRevenue cannot be null");
        }
        if (incomeStatement.getTotalExpense() == null) {
            throw new IllegalArgumentException("totalExpense cannot be null");
        }
        if (incomeStatement.getNetIncome() == null) {
            throw new IllegalArgumentException("netIncome cannot be null");
        }

        if (incomeStatement.getTotalRevenue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalRevenue cannot be negative");
        }
        if (incomeStatement.getTotalExpense().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalExpense cannot be negative");
        }

        BigDecimal expectedNetIncome = incomeStatement.getTotalRevenue().subtract(incomeStatement.getTotalExpense());
        if (incomeStatement.getNetIncome().compareTo(expectedNetIncome) != 0) {
            throw new IllegalArgumentException("Income statement calculation failed: netIncome must equal totalRevenue - totalExpense");
        }
    }
}