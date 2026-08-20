package com.globaldynamicssystems.aurum.accounting.service;

import java.time.LocalDate;

public interface AccountingPeriodGuard {

    void guardJournalEntry(Long fiscalPeriodId, LocalDate accountingDate);

    void guardPosting(Long fiscalPeriodId, LocalDate accountingDate);

    void guardOpeningBalance(Long fiscalPeriodId, LocalDate accountingDate);
}