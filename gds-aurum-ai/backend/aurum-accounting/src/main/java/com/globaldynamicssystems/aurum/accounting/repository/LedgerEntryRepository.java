package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByJournalEntryId(Long journalEntryId);

    List<LedgerEntry> findByAccountId(Long accountId);

    List<LedgerEntry> findByFiscalPeriodId(Long fiscalPeriodId);

    List<LedgerEntry> findByAccountIdAndFiscalPeriodId(Long accountId, Long fiscalPeriodId);

    boolean existsByJournalEntryId(Long journalEntryId);

    @Query("""
            SELECT DISTINCT le FROM LedgerEntry le
            JOIN le.dimensions d
            WHERE d.dimension.dimensionType = :type
            AND d.dimension.referenceId = :referenceId
            AND le.fiscalPeriod.id = :fiscalPeriodId
            """)
    List<LedgerEntry> findByDimensionTypeAndReferenceIdAndFiscalPeriodId(
            @Param("type") AnalyticalDimensionType type,
            @Param("referenceId") Long referenceId,
            @Param("fiscalPeriodId") Long fiscalPeriodId
    );

    @Query("""
            SELECT DISTINCT le FROM LedgerEntry le
            JOIN le.dimensions d
            WHERE d.dimension.dimensionType = :type
            AND le.fiscalPeriod.id = :fiscalPeriodId
            """)
    List<LedgerEntry> findByDimensionTypeAndFiscalPeriodId(
            @Param("type") AnalyticalDimensionType type,
            @Param("fiscalPeriodId") Long fiscalPeriodId
    );

    List<LedgerEntry> findByAccountIdInAndFiscalPeriodId(List<Long> accountIds, Long fiscalPeriodId);

    @Query("""
            SELECT le FROM LedgerEntry le
            WHERE le.account.chartOfAccounts.id = :chartOfAccountsId
            AND le.fiscalPeriod.id = :fiscalPeriodId
            """)
    List<LedgerEntry> findByChartOfAccountsIdAndFiscalPeriodId(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalPeriodId") Long fiscalPeriodId
    );
}