package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityFilter;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.service.FilteredProfitabilityService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultFilteredProfitabilityService implements FilteredProfitabilityService {

    private final ProfitabilityService profitabilityService;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;

    public DefaultFilteredProfitabilityService(ProfitabilityService profitabilityService,
                                               AnalyticalDimensionValueRepository dimensionValueRepository) {
        this.profitabilityService = profitabilityService;
        this.dimensionValueRepository = dimensionValueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfitabilityReport generate(ProfitabilityFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("ProfitabilityFilter cannot be null");
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

        return profitabilityService.generate(
                filter.getChartOfAccountsId(),
                filter.getFiscalPeriodId(),
                filter.getDimensionType(),
                dimensionIds);
    }
}
