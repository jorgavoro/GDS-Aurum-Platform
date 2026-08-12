package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryBalanceService;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultJournalEntryValidator implements JournalEntryValidator {

    private final JournalEntryBalanceService balanceService;

    public DefaultJournalEntryValidator(JournalEntryBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public void validate(JournalEntry journalEntry) {
        if (journalEntry == null) {
            throw new IllegalArgumentException("JournalEntry cannot be null");
        }
        if (journalEntry.getDocumentNumber() == null || journalEntry.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number is mandatory");
        }
        if (journalEntry.getAccountingDate() == null) {
            throw new IllegalArgumentException("Accounting date is mandatory");
        }
        if (journalEntry.getDescription() == null || journalEntry.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is mandatory");
        }
        if (journalEntry.getChartOfAccounts() == null) {
            throw new IllegalArgumentException("ChartOfAccounts is mandatory");
        }
        if (journalEntry.getFiscalPeriod() == null) {
            throw new IllegalArgumentException("FiscalPeriod is mandatory");
        }
        if (journalEntry.getLines() == null || journalEntry.getLines().isEmpty()) {
            throw new IllegalArgumentException("JournalEntry must contain at least one line");
        }

        if (!FiscalPeriodStatus.OPEN.equals(journalEntry.getFiscalPeriod().getStatus())) {
            throw new IllegalArgumentException("FiscalPeriod must be OPEN to register journal entries");
        }

        if (journalEntry.getAccountingDate().isBefore(journalEntry.getFiscalPeriod().getStartDate()) ||
            journalEntry.getAccountingDate().isAfter(journalEntry.getFiscalPeriod().getEndDate())) {
            throw new IllegalArgumentException("Accounting date (" + journalEntry.getAccountingDate()
                    + ") must be within FiscalPeriod range ("
                    + journalEntry.getFiscalPeriod().getStartDate() + " to "
                    + journalEntry.getFiscalPeriod().getEndDate() + ")");
        }

        if (journalEntry.getFiscalPeriod().getChartOfAccounts() != null &&
            !journalEntry.getFiscalPeriod().getChartOfAccounts().getId().equals(journalEntry.getChartOfAccounts().getId())) {
            throw new IllegalArgumentException("FiscalPeriod does not belong to the JournalEntry ChartOfAccounts");
        }

        Set<Integer> lineNumbers = new HashSet<>();

        for (JournalEntryLine line : journalEntry.getLines()) {
            if (line == null) {
                throw new IllegalArgumentException("JournalEntryLine cannot be null");
            }
            if (line.getLineNumber() == null || line.getLineNumber() <= 0) {
                throw new IllegalArgumentException("Line number must be positive");
            }
            if (!lineNumbers.add(line.getLineNumber())) {
                throw new IllegalArgumentException("Duplicate line number detected: " + line.getLineNumber());
            }

            Account account = line.getAccount();
            if (account == null) {
                throw new IllegalArgumentException("Line " + line.getLineNumber() + " must have an Account");
            }
            if (account.getActive() != null && !account.getActive()) {
                throw new IllegalArgumentException("Account in line " + line.getLineNumber() + " is inactive");
            }
            if (account.getPostable() != null && !account.getPostable()) {
                throw new IllegalArgumentException("Account in line " + line.getLineNumber() + " is not postable");
            }
            if (account.getChartOfAccounts() != null &&
                !account.getChartOfAccounts().getId().equals(journalEntry.getChartOfAccounts().getId())) {
                throw new IllegalArgumentException("Account in line " + line.getLineNumber() 
                        + " does not belong to the same ChartOfAccounts");
            }

            BigDecimal debit = line.getDebit();
            BigDecimal credit = line.getCredit();

            if (debit == null || credit == null) {
                throw new IllegalArgumentException("Debit and credit amounts cannot be null in line " + line.getLineNumber());
            }
            if (debit.compareTo(BigDecimal.ZERO) < 0 || credit.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Negative values are not allowed in line " + line.getLineNumber());
            }

            boolean isDebitPositive = debit.compareTo(BigDecimal.ZERO) > 0;
            boolean isCreditPositive = credit.compareTo(BigDecimal.ZERO) > 0;

            if (isDebitPositive && isCreditPositive) {
                throw new IllegalArgumentException("Line " + line.getLineNumber() 
                        + " cannot have both debit and credit greater than zero");
            }
            if (!isDebitPositive && !isCreditPositive) {
                throw new IllegalArgumentException("Line " + line.getLineNumber() 
                        + " must have either debit or credit greater than zero");
            }
        }

        if (!balanceService.isBalanced(journalEntry)) {
            throw new IllegalArgumentException("JournalEntry is not balanced: Total Debits (" 
                    + balanceService.calculateDebitTotal(journalEntry) 
                    + ") must equal Total Credits (" 
                    + balanceService.calculateCreditTotal(journalEntry) + ")");
        }
    }
}