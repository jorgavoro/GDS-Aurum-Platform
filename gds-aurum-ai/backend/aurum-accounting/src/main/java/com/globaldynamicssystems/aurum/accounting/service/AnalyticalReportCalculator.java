package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface AnalyticalReportCalculator {

    BigDecimal calculateDebit(List<LedgerEntry> entries);

    BigDecimal calculateCredit(List<LedgerEntry> entries);

    BigDecimal calculateBalance(List<LedgerEntry> entries);

    BigDecimal calculateRevenue(List<LedgerEntry> entries);

    BigDecimal calculateExpense(List<LedgerEntry> entries);

    BigDecimal calculateNetResult(List<LedgerEntry> entries);
}
