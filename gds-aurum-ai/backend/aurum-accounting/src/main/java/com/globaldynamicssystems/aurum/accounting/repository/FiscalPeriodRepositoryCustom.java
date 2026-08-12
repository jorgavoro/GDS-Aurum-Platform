package com.globaldynamicssystems.aurum.accounting.repository;

import java.time.LocalDate;

public interface FiscalPeriodRepositoryCustom {

    boolean existsOverlappingPeriod(Long chartOfAccountsId, LocalDate startDate, LocalDate endDate);
}