package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByJournalEntryId(Long journalEntryId);

    List<LedgerEntry> findByAccountId(Long accountId);

    List<LedgerEntry> findByFiscalPeriodId(Long fiscalPeriodId);

    List<LedgerEntry> findByAccountIdAndFiscalPeriodId(Long accountId, Long fiscalPeriodId);

    boolean existsByJournalEntryId(Long journalEntryId);
}