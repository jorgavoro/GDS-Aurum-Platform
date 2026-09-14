package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DefaultFinancialStatementCalculator implements FinancialStatementCalculator {

    public DefaultFinancialStatementCalculator() {
    }

    @Override
    public BigDecimal calculateAccountBalance(List<LedgerEntry> entries) {
        BigDecimal totalDebit = sumDebit(entries);
        BigDecimal totalCredit = sumCredit(entries);
        return totalDebit.subtract(totalCredit);
    }

    @Override
    public BigDecimal calculateRevenue(List<LedgerEntry> entries) {
        BigDecimal totalDebit = sumDebit(entries);
        BigDecimal totalCredit = sumCredit(entries);
        return totalCredit.subtract(totalDebit);
    }

    @Override
    public BigDecimal calculateExpense(List<LedgerEntry> entries) {
        BigDecimal totalDebit = sumDebit(entries);
        BigDecimal totalCredit = sumCredit(entries);
        return totalDebit.subtract(totalCredit);
    }

    private BigDecimal sumDebit(List<LedgerEntry> entries) {
        BigDecimal total = BigDecimal.ZERO;
        if (entries != null) {
            for (LedgerEntry entry : entries) {
                if (entry != null && entry.getDebit() != null) {
                    total = total.add(entry.getDebit());
                }
            }
        }
        return total;
    }

    private BigDecimal sumCredit(List<LedgerEntry> entries) {
        BigDecimal total = BigDecimal.ZERO;
        if (entries != null) {
            for (LedgerEntry entry : entries) {
                if (entry != null && entry.getCredit() != null) {
                    total = total.add(entry.getCredit());
                }
            }
        }
        return total;
    }
}