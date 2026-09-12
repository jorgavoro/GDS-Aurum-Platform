package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLineDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryLineDimensionRepository extends JpaRepository<JournalEntryLineDimension, Long> {

    List<JournalEntryLineDimension> findByJournalEntryLineId(Long journalEntryLineId);

    List<JournalEntryLineDimension> findByDimensionId(Long dimensionId);
}
