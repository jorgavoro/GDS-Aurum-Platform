package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FiscalPeriodService {

    FiscalPeriod create(Long chartOfAccountsId, FiscalPeriod fiscalPeriod);

    Optional<FiscalPeriod> findById(Long id);

    List<FiscalPeriod> findByChartOfAccounts(Long chartOfAccountsId);

    Optional<FiscalPeriod> findByDate(Long chartOfAccountsId, LocalDate date);

    FiscalPeriod close(Long id);
}