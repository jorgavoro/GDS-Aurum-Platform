package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepositoryCustom;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodService;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultFiscalPeriodService implements FiscalPeriodService {

    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final FiscalPeriodValidator fiscalPeriodValidator;
    private final FiscalPeriodRepositoryCustom fiscalPeriodRepositoryCustom;

    public DefaultFiscalPeriodService(FiscalPeriodRepository fiscalPeriodRepository,
                                      ChartOfAccountsRepository chartOfAccountsRepository,
                                      FiscalPeriodValidator fiscalPeriodValidator,
                                      FiscalPeriodRepositoryCustom fiscalPeriodRepositoryCustom) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.fiscalPeriodValidator = fiscalPeriodValidator;
        this.fiscalPeriodRepositoryCustom = fiscalPeriodRepositoryCustom;
    }

    @Override
    public FiscalPeriod create(Long chartOfAccountsId, FiscalPeriod fiscalPeriod) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("ChartOfAccounts ID cannot be null");
        }

        fiscalPeriodValidator.validate(fiscalPeriod);

        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException("ChartOfAccounts not found with ID: " + chartOfAccountsId));

        if (fiscalPeriodRepository.findByChartOfAccountsIdAndFiscalYearAndPeriodNumber(
                chartOfAccountsId, fiscalPeriod.getFiscalYear(), fiscalPeriod.getPeriodNumber()).isPresent()) {
            throw new IllegalArgumentException("FiscalPeriod already exists for year " 
                    + fiscalPeriod.getFiscalYear() + " and period " + fiscalPeriod.getPeriodNumber());
        }

        if (fiscalPeriodRepositoryCustom.existsOverlappingPeriod(
                chartOfAccountsId, fiscalPeriod.getStartDate(), fiscalPeriod.getEndDate())) {
            throw new IllegalArgumentException("FiscalPeriod date range overlaps with an existing period for the same ChartOfAccounts");
        }

        fiscalPeriod.setChartOfAccounts(chartOfAccounts);
        fiscalPeriod.setStatus(FiscalPeriodStatus.OPEN);
        fiscalPeriod.setActive(true);

        return fiscalPeriodRepository.save(fiscalPeriod);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FiscalPeriod> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return fiscalPeriodRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FiscalPeriod> findByChartOfAccounts(Long chartOfAccountsId) {
        if (chartOfAccountsId == null) {
            return List.of();
        }
        return fiscalPeriodRepository.findByChartOfAccountsId(chartOfAccountsId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FiscalPeriod> findByDate(Long chartOfAccountsId, LocalDate date) {
        if (chartOfAccountsId == null || date == null) {
            return Optional.empty();
        }
        return fiscalPeriodRepository.findByChartOfAccountsIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                chartOfAccountsId, date, date
        );
    }

    @Override
    public FiscalPeriod close(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("FiscalPeriod ID cannot be null");
        }

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FiscalPeriod not found with ID: " + id));

        if (FiscalPeriodStatus.CLOSED.equals(fiscalPeriod.getStatus())) {
            return fiscalPeriod;
        }

        fiscalPeriod.setStatus(FiscalPeriodStatus.CLOSED);

        return fiscalPeriodRepository.save(fiscalPeriod);
    }
}