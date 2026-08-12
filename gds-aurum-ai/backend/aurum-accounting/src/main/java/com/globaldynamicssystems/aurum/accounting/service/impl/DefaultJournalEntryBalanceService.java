package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryBalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultJournalEntryBalanceService implements JournalEntryBalanceService {

    public DefaultJournalEntryBalanceService() {
    }

    @Override
    public BigDecimal calculateDebitTotal(JournalEntry journalEntry) {
        if (journalEntry == null || journalEntry.getLines() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalDebit = BigDecimal.ZERO;
        for (JournalEntryLine line : journalEntry.getLines()) {
            if (line != null && line.getDebit() != null) {
                totalDebit = totalDebit.add(line.getDebit());
            }
        }
        return totalDebit;
    }

    @Override
    public BigDecimal calculateCreditTotal(JournalEntry journalEntry) {
        if (journalEntry == null || journalEntry.getLines() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalCredit = BigDecimal.ZERO;
        for (JournalEntryLine line : journalEntry.getLines()) {
            if (line != null && line.getCredit() != null) {
                totalCredit = totalCredit.add(line.getCredit());
            }
        }
        return totalCredit;
    }

    @Override
    public boolean isBalanced(JournalEntry journalEntry) {
        BigDecimal totalDebit = calculateDebitTotal(journalEntry);
        BigDecimal totalCredit = calculateCreditTotal(journalEntry);

        return totalDebit.compareTo(totalCredit) == 0;
    }
}