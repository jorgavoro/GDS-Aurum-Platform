package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DefaultAccountFinancialAnalysisCalculator implements AccountFinancialAnalysisCalculator {

    @Override
    public BigDecimal calculateDebit(List<LedgerEntry> entries) {
        if (entries == null) {
            return BigDecimal.ZERO;
        }
        return entries.stream()
                .map(e -> e.getDebit() != null ? e.getDebit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCredit(List<LedgerEntry> entries) {
        if (entries == null) {
            return BigDecimal.ZERO;
        }
        return entries.stream()
                .map(e -> e.getCredit() != null ? e.getCredit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateBalance(List<LedgerEntry> entries) {
        return calculateDebit(entries).subtract(calculateCredit(entries));
    }

    @Override
    public BigDecimal calculateRevenue(List<LedgerEntry> entries) {
        if (entries == null) {
            return BigDecimal.ZERO;
        }
        return entries.stream()
                .filter(e -> e.getAccount() != null
                        && AccountType.REVENUE.equals(e.getAccount().getAccountType()))
                .map(e -> {
                    BigDecimal credit = e.getCredit() != null ? e.getCredit() : BigDecimal.ZERO;
                    BigDecimal debit = e.getDebit() != null ? e.getDebit() : BigDecimal.ZERO;
                    return credit.subtract(debit);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateExpense(List<LedgerEntry> entries) {
        if (entries == null) {
            return BigDecimal.ZERO;
        }
        return entries.stream()
                .filter(e -> e.getAccount() != null
                        && AccountType.EXPENSE.equals(e.getAccount().getAccountType()))
                .map(e -> {
                    BigDecimal debit = e.getDebit() != null ? e.getDebit() : BigDecimal.ZERO;
                    BigDecimal credit = e.getCredit() != null ? e.getCredit() : BigDecimal.ZERO;
                    return debit.subtract(credit);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateNetResult(BigDecimal revenue, BigDecimal expense) {
        BigDecimal r = revenue != null ? revenue : BigDecimal.ZERO;
        BigDecimal e = expense != null ? expense : BigDecimal.ZERO;
        return r.subtract(e);
    }
}
