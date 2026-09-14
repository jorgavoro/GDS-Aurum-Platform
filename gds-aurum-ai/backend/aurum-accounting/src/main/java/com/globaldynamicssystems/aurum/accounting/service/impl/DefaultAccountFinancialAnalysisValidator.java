package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisReport;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultAccountFinancialAnalysisValidator implements AccountFinancialAnalysisValidator {

    @Override
    public void validate(AccountFinancialAnalysisReport report) {
        if (report == null) {
            throw new IllegalStateException("AccountFinancialAnalysisReport cannot be null");
        }
        if (report.getChartOfAccountsId() == null) {
            throw new IllegalStateException("chartOfAccountsId cannot be null");
        }
        if (report.getFiscalPeriodId() == null) {
            throw new IllegalStateException("fiscalPeriodId cannot be null");
        }
        if (report.getAccounts() == null) {
            throw new IllegalStateException("accounts cannot be null");
        }
        if (report.getTotalDebit() == null) {
            throw new IllegalStateException("totalDebit cannot be null");
        }
        if (report.getTotalCredit() == null) {
            throw new IllegalStateException("totalCredit cannot be null");
        }
        if (report.getTotalBalance() == null) {
            throw new IllegalStateException("totalBalance cannot be null");
        }
        if (report.getTotalRevenue() == null) {
            throw new IllegalStateException("totalRevenue cannot be null");
        }
        if (report.getTotalExpense() == null) {
            throw new IllegalStateException("totalExpense cannot be null");
        }
        if (report.getTotalNetResult() == null) {
            throw new IllegalStateException("totalNetResult cannot be null");
        }

        BigDecimal expectedBalance = report.getTotalDebit().subtract(report.getTotalCredit());
        if (expectedBalance.compareTo(report.getTotalBalance()) != 0) {
            throw new IllegalStateException(
                    "totalBalance must equal totalDebit - totalCredit");
        }

        BigDecimal expectedNetResult = report.getTotalRevenue().subtract(report.getTotalExpense());
        if (expectedNetResult.compareTo(report.getTotalNetResult()) != 0) {
            throw new IllegalStateException(
                    "totalNetResult must equal totalRevenue - totalExpense");
        }
    }
}
