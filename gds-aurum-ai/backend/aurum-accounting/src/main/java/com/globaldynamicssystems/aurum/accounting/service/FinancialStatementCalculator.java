package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface FinancialStatementCalculator {

    BigDecimal calculateAccountBalance(List<LedgerEntry> entries);

    BigDecimal calculateRevenue(List<LedgerEntry> entries);

    BigDecimal calculateExpense(List<LedgerEntry> entries);
}