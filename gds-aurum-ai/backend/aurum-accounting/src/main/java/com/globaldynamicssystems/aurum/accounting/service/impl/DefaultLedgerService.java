package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.LedgerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DefaultLedgerService implements LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public DefaultLedgerService(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    public List<LedgerEntry> findByAccount(Long accountId) {
        if (accountId == null) {
            return List.of();
        }
        return ledgerEntryRepository.findByAccountId(accountId);
    }

    @Override
    public List<LedgerEntry> findByAccountAndFiscalPeriod(Long accountId, Long fiscalPeriodId) {
        if (accountId == null || fiscalPeriodId == null) {
            return List.of();
        }
        return ledgerEntryRepository.findByAccountIdAndFiscalPeriodId(accountId, fiscalPeriodId);
    }

    @Override
    public List<LedgerEntry> findByJournalEntry(Long journalEntryId) {
        if (journalEntryId == null) {
            return List.of();
        }
        return ledgerEntryRepository.findByJournalEntryId(journalEntryId);
    }

    @Override
    public BigDecimal calculateDebitTotal(Long accountId, Long fiscalPeriodId) {
        List<LedgerEntry> entries = findByAccountAndFiscalPeriod(accountId, fiscalPeriodId);
        BigDecimal totalDebit = BigDecimal.ZERO;
        for (LedgerEntry entry : entries) {
            if (entry != null && entry.getDebit() != null) {
                totalDebit = totalDebit.add(entry.getDebit());
            }
        }
        return totalDebit;
    }

    @Override
    public BigDecimal calculateCreditTotal(Long accountId, Long fiscalPeriodId) {
        List<LedgerEntry> entries = findByAccountAndFiscalPeriod(accountId, fiscalPeriodId);
        BigDecimal totalCredit = BigDecimal.ZERO;
        for (LedgerEntry entry : entries) {
            if (entry != null && entry.getCredit() != null) {
                totalCredit = totalCredit.add(entry.getCredit());
            }
        }
        return totalCredit;
    }

    @Override
    public BigDecimal calculateBalance(Long accountId, Long fiscalPeriodId) {
        BigDecimal totalDebit = calculateDebitTotal(accountId, fiscalPeriodId);
        BigDecimal totalCredit = calculateCreditTotal(accountId, fiscalPeriodId);
        return totalDebit.subtract(totalCredit);
    }
}