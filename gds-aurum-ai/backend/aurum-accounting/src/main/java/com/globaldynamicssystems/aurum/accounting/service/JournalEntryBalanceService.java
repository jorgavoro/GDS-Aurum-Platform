package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;

import java.math.BigDecimal;

public interface JournalEntryBalanceService {

    BigDecimal calculateDebitTotal(JournalEntry journalEntry);

    BigDecimal calculateCreditTotal(JournalEntry journalEntry);

    boolean isBalanced(JournalEntry journalEntry);
}