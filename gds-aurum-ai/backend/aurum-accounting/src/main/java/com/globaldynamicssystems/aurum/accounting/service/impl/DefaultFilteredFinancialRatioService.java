package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;
import com.globaldynamicssystems.aurum.accounting.service.FilteredFinancialRatioService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultFilteredFinancialRatioService implements FilteredFinancialRatioService {

    private final FinancialRatioService ratioService;

    public DefaultFilteredFinancialRatioService(FinancialRatioService ratioService) {
        this.ratioService = ratioService;
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialRatioReport calculate(FinancialRatioFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("FinancialRatioFilter cannot be null");
        }

        FinancialRatioReport fullReport = ratioService.calculate(
                filter.getChartOfAccountsId(), filter.getFiscalPeriodId());

        if (filter.getRatioTypes() == null || filter.getRatioTypes().isEmpty()) {
            return fullReport;
        }

        List<FinancialRatio> filtered = fullReport.getRatios().stream()
                .filter(r -> filter.getRatioTypes().contains(r.getType()))
                .toList();

        return new FinancialRatioReport(
                fullReport.getChartOfAccountsId(),
                fullReport.getFiscalPeriodId(),
                fullReport.getFiscalPeriodName(),
                filtered);
    }
}
