package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FiscalPeriodClosingQueryRepository extends JpaRepository<JournalEntry, Long> {

    @Query("SELECT COUNT(j) FROM JournalEntry j WHERE j.fiscalPeriod.id = :fiscalPeriodId AND j.status = com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus.DRAFT")
    long countDraftEntries(@Param("fiscalPeriodId") Long fiscalPeriodId);

    @Query("SELECT COUNT(j) FROM JournalEntry j WHERE j.fiscalPeriod.id = :fiscalPeriodId AND j.status = com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus.VALIDATED")
    long countValidatedEntries(@Param("fiscalPeriodId") Long fiscalPeriodId);

    @Query("SELECT COUNT(j) FROM JournalEntry j WHERE j.fiscalPeriod.id = :fiscalPeriodId AND j.status = com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus.POSTED")
    long countPostedEntries(@Param("fiscalPeriodId") Long fiscalPeriodId);

    @Query("SELECT COUNT(j) FROM JournalEntry j WHERE j.fiscalPeriod.id = :fiscalPeriodId AND j.status = com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus.POSTED AND NOT EXISTS (SELECT l FROM LedgerEntry l WHERE l.journalEntry.id = j.id)")
    long countPostedEntriesWithoutLedger(@Param("fiscalPeriodId") Long fiscalPeriodId);
}