package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FiscalPeriodRepository extends JpaRepository<FiscalPeriod, Long> {

    List<FiscalPeriod> findByChartOfAccountsId(Long chartOfAccountsId);

    Optional<FiscalPeriod> findByChartOfAccountsIdAndFiscalYearAndPeriodNumber(
            Long chartOfAccountsId,
            Integer fiscalYear,
            Integer periodNumber
    );

    List<FiscalPeriod> findByChartOfAccountsIdAndStatus(
            Long chartOfAccountsId,
            FiscalPeriodStatus status
    );

    boolean existsByChartOfAccountsIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long chartOfAccountsId,
            LocalDate endDate,
            LocalDate startDate
    );

    Optional<FiscalPeriod> findByChartOfAccountsIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long chartOfAccountsId,
            LocalDate dateForStart,
            LocalDate dateForEnd
    );
}