package com.globaldynamicssystems.aurum.accounting.repository;

public interface FiscalPeriodClosingRepository {

    boolean existsOpenJournalEntries(Long fiscalPeriodId);
}