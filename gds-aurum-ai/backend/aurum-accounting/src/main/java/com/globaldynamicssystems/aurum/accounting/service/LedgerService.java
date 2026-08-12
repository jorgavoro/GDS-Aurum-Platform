package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerService {

    List<LedgerEntry> findByAccount(Long accountId);

    List<LedgerEntry> findByAccountAndFiscalPeriod(Long accountId, Long fiscalPeriodId);

    List<LedgerEntry> findByJournalEntry(Long journalEntryId);

    BigDecimal calculateDebitTotal(Long accountId, Long fiscalPeriodId);

    BigDecimal calculateCreditTotal(Long accountId, Long fiscalPeriodId);

    BigDecimal calculateBalance(Long accountId, Long fiscalPeriodId);
}