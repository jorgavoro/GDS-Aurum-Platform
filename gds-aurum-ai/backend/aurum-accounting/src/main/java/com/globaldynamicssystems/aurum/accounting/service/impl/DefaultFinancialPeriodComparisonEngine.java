package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriodResult;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialPeriodComparisonRequest;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonPeriodService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialPeriodAggregationService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialPeriodComparisonEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultFinancialPeriodComparisonEngine implements FinancialPeriodComparisonEngine {

    private final FinancialComparisonPeriodService periodService;
    private final FinancialPeriodAggregationService aggregationService;
    private final FinancialComparisonService comparisonService;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;

    public DefaultFinancialPeriodComparisonEngine(FinancialComparisonPeriodService periodService,
                                                  FinancialPeriodAggregationService aggregationService,
                                                  FinancialComparisonService comparisonService,
                                                  AnalyticalDimensionValueRepository dimensionValueRepository) {
        this.periodService = periodService;
        this.aggregationService = aggregationService;
        this.comparisonService = comparisonService;
        this.dimensionValueRepository = dimensionValueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialComparisonPeriodResult compare(FinancialPeriodComparisonRequest request) {
        validateRequest(request);

        FinancialComparisonPeriod currentPeriod = periodService.resolveCurrentPeriod(
                request.getCurrentFiscalYear(),
                request.getCurrentPeriodNumber(),
                request.getPeriodType());

        FinancialComparisonPeriod previousPeriod = periodService.resolvePreviousPeriod(
                request.getCurrentFiscalYear(),
                request.getCurrentPeriodNumber(),
                request.getPeriodType());

        List<Long> currentPeriodIds = aggregationService.resolveFiscalPeriodIds(
                request.getChartOfAccountsId(), currentPeriod);
        List<Long> previousPeriodIds = aggregationService.resolveFiscalPeriodIds(
                request.getChartOfAccountsId(), previousPeriod);

        List<Long> dimensionIds = resolveDimensionIds(request);

        FinancialComparisonReport report = comparisonService.compare(
                request.getChartOfAccountsId(),
                currentPeriodIds,
                previousPeriodIds,
                currentPeriod.getLabel(),
                previousPeriod.getLabel(),
                request.getDimensionType(),
                dimensionIds);

        return new FinancialComparisonPeriodResult(currentPeriod, previousPeriod, report);
    }

    private List<Long> resolveDimensionIds(FinancialPeriodComparisonRequest request) {
        if (request.getDimensionIds() != null) {
            return request.getDimensionIds();
        }
        if (Boolean.TRUE.equals(request.getIncludeInactive())) {
            List<AnalyticalDimensionValue> all = dimensionValueRepository
                    .findByDimensionType(request.getDimensionType());
            return all.stream().map(AnalyticalDimensionValue::getReferenceId).toList();
        }
        return null;
    }

    private void validateRequest(FinancialPeriodComparisonRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("FinancialPeriodComparisonRequest cannot be null");
        }
        if (request.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (request.getPeriodType() == null) {
            throw new IllegalArgumentException("periodType cannot be null");
        }
        if (request.getCurrentFiscalYear() == null) {
            throw new IllegalArgumentException("currentFiscalYear cannot be null");
        }
        if (request.getCurrentPeriodNumber() == null) {
            throw new IllegalArgumentException("currentPeriodNumber cannot be null");
        }
        if (request.getDimensionType() == null) {
            throw new IllegalArgumentException("dimensionType cannot be null");
        }
    }
}
