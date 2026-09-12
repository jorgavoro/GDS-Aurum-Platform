package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReportFilter;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportQueryService;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultAnalyticalReportQueryService implements AnalyticalReportQueryService {

    private final AnalyticalReportingService reportingService;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;

    public DefaultAnalyticalReportQueryService(AnalyticalReportingService reportingService,
                                               AnalyticalDimensionValueRepository dimensionValueRepository) {
        this.reportingService = reportingService;
        this.dimensionValueRepository = dimensionValueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyticalReport generate(AnalyticalReportFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("AnalyticalReportFilter cannot be null");
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

        return reportingService.generate(
                filter.getChartOfAccountsId(),
                filter.getFiscalPeriodId(),
                filter.getDimensionType(),
                dimensionIds);
    }
}
