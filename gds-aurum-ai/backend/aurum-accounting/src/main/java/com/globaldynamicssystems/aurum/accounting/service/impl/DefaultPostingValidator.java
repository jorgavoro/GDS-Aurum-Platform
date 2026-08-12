package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus;
import com.globaldynamicssystems.aurum.accounting.service.PostingValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultPostingValidator implements PostingValidator {

    public DefaultPostingValidator() {
    }

    @Override
    public void validate(JournalEntry journalEntry) {
        if (journalEntry == null) {
            throw new IllegalArgumentException("JournalEntry cannot be null");
        }
        if (!JournalEntryStatus.VALIDATED.equals(journalEntry.getStatus())) {
            throw new IllegalArgumentException("JournalEntry status must be VALIDATED to be posted");
        }

        FiscalPeriod fiscalPeriod = journalEntry.getFiscalPeriod();
        if (fiscalPeriod == null) {
            throw new IllegalArgumentException("FiscalPeriod is mandatory for posting");
        }
        if (!FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus())) {
            throw new IllegalArgumentException("FiscalPeriod must be OPEN to post journal entries");
        }

        if (journalEntry.getLines() == null || journalEntry.getLines().isEmpty()) {
            throw new IllegalArgumentException("JournalEntry must contain lines to be posted");
        }

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        for (JournalEntryLine line : journalEntry.getLines()) {
            if (line == null) {
                throw new IllegalArgumentException("JournalEntryLine cannot be null");
            }
            Account account = line.getAccount();
            if (account == null) {
                throw new IllegalArgumentException("Account is mandatory for each line");
            }
            if (account.getActive() != null && !account.getActive()) {
                throw new IllegalArgumentException("Account with ID " + account.getId() + " is inactive");
            }
            if (account.getPostable() != null && !account.getPostable()) {
                throw new IllegalArgumentException("Account with ID " + account.getId() + " is not postable");
            }
            if (journalEntry.getChartOfAccounts() != null && account.getChartOfAccounts() != null &&
                !account.getChartOfAccounts().getId().equals(journalEntry.getChartOfAccounts().getId())) {
                throw new IllegalArgumentException("Account in line " + line.getLineNumber() 
                        + " does not belong to the same ChartOfAccounts as the JournalEntry");
            }

            if (line.getDebit() != null) {
                totalDebit = totalDebit.add(line.getDebit());
            }
            if (line.getCredit() != null) {
                totalCredit = totalCredit.add(line.getCredit());
            }
        }

        if (totalDebit.compareTo(totalCredit) != 0) {
            throw new IllegalArgumentException("JournalEntry is not balanced: Total Debits (" 
                    + totalDebit + ") must equal Total Credits (" + totalCredit + ")");
        }
    }
}