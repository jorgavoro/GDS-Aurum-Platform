package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    Optional<JournalEntry> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);

    List<JournalEntry> findByFiscalPeriodId(Long fiscalPeriodId);

    List<JournalEntry> findByChartOfAccountsId(Long chartOfAccountsId);
}