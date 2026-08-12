package com.globaldynamicssystems.aurum.accounting.repository.impl;

import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepositoryCustom;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class FiscalPeriodRepositoryCustomImpl implements FiscalPeriodRepositoryCustom {

    private final FiscalPeriodRepository fiscalPeriodRepository;

    public FiscalPeriodRepositoryCustomImpl(@Lazy FiscalPeriodRepository fiscalPeriodRepository) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
    }

    @Override
    public boolean existsOverlappingPeriod(Long chartOfAccountsId, LocalDate startDate, LocalDate endDate) {
        if (chartOfAccountsId == null || startDate == null || endDate == null) {
            return false;
        }

        return fiscalPeriodRepository.existsByChartOfAccountsIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                chartOfAccountsId,
                endDate,
                startDate
        );
    }
}