package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.service.FilteredFinancialComparisonService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultFilteredFinancialComparisonService implements FilteredFinancialComparisonService {

    private final FinancialComparisonService comparisonService;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;

    public DefaultFilteredFinancialComparisonService(FinancialComparisonService comparisonService,
                                                     AnalyticalDimensionValueRepository dimensionValueRepository) {
        this.comparisonService = comparisonService;
        this.dimensionValueRepository = dimensionValueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialComparisonReport compare(FinancialComparisonFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("FinancialComparisonFilter cannot be null");
        }
        if (filter.getDimensionType() == null) {
            throw new IllegalArgumentException("dimensionType cannot be null");
        }

        List<Long> dimensionIds = filter.getDimensionIds();

        if (Boolean.TRUE.equals(filter.getIncludeInactive()) && dimensionIds == null) {
            List<AnalyticalDimensionValue> all = dimensionValueRepository
                    .findByDimensionType(filter.getDimensionType());
            dimensionIds = all.stream()
                    .map(AnalyticalDimensionValue::getReferenceId)
                    .toList();
        }

        return comparisonService.compare(
                filter.getChartOfAccountsId(),
                filter.getCurrentFiscalPeriodId(),
                filter.getPreviousFiscalPeriodId(),
                filter.getDimensionType(),
                dimensionIds);
    }
}
