package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FiscalYearClosingRepository extends JpaRepository<FiscalPeriod, Long> {

    @Query("SELECT COUNT(fp) FROM FiscalPeriod fp WHERE (fp.chartOfAccounts.id = :chartOfAccountsId OR fp.chartOfAccountsId = :chartOfAccountsId) AND fp.fiscalYear = :fiscalYear")
    long countPeriodsByFiscalYear(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COUNT(fp) FROM FiscalPeriod fp WHERE (fp.chartOfAccounts.id = :chartOfAccountsId OR fp.chartOfAccountsId = :chartOfAccountsId) AND fp.fiscalYear = :fiscalYear AND (fp.status = 'CLOSED' OR UPPER(CAST(fp.status AS string)) = 'CLOSED')")
    long countClosedPeriodsByFiscalYear(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COUNT(je) > 0 FROM JournalEntry je WHERE (je.chartOfAccounts.id = :chartOfAccountsId OR je.chartOfAccountsId = :chartOfAccountsId) AND (je.fiscalYear = :fiscalYear OR je.fiscalPeriod.fiscalYear = :fiscalYear) AND je.description LIKE 'Year End Closing%'")
    boolean existsClosingEntry(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COUNT(je) FROM JournalEntry je WHERE (je.chartOfAccounts.id = :chartOfAccountsId OR je.chartOfAccountsId = :chartOfAccountsId) AND (je.fiscalYear = :fiscalYear OR je.fiscalPeriod.fiscalYear = :fiscalYear) AND (je.status = 'DRAFT' OR UPPER(CAST(je.status AS string)) = 'DRAFT')")
    long countDraftJournalEntries(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COUNT(je) FROM JournalEntry je WHERE (je.chartOfAccounts.id = :chartOfAccountsId OR je.chartOfAccountsId = :chartOfAccountsId) AND (je.fiscalYear = :fiscalYear OR je.fiscalPeriod.fiscalYear = :fiscalYear) AND (je.status = 'VALIDATED' OR UPPER(CAST(je.status AS string)) = 'VALIDATED')")
    long countValidatedJournalEntries(
            @Param("chartOfAccountsId") Long chartOfAccountsId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COALESCE(SUM(le.debit), 0) FROM LedgerEntry le WHERE (le.account.id = :accountId OR le.accountId = :accountId) AND (le.fiscalYear = :fiscalYear OR le.fiscalPeriod.fiscalYear = :fiscalYear)")
    BigDecimal sumDebitByAccountAndFiscalYear(
            @Param("accountId") Long accountId,
            @Param("fiscalYear") Integer fiscalYear
    );

    @Query("SELECT COALESCE(SUM(le.credit), 0) FROM LedgerEntry le WHERE (le.account.id = :accountId OR le.accountId = :accountId) AND (le.fiscalYear = :fiscalYear OR le.fiscalPeriod.fiscalYear = :fiscalYear)")
    BigDecimal sumCreditByAccountAndFiscalYear(
            @Param("accountId") Long accountId,
            @Param("fiscalYear") Integer fiscalYear
    );
}