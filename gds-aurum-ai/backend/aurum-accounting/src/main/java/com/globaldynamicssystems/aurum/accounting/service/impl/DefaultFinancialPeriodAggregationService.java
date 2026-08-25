package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialPeriodAggregationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultFinancialPeriodAggregationService implements FinancialPeriodAggregationService {

    private final FiscalPeriodRepository fiscalPeriodRepository;

    public DefaultFinancialPeriodAggregationService(FiscalPeriodRepository fiscalPeriodRepository) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
    }

    @Override
    public List<Long> resolveFiscalPeriodIds(Long chartOfAccountsId, FinancialComparisonPeriod period) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (period == null) {
            throw new IllegalArgumentException("period cannot be null");
        }

        return switch (period.getType()) {
            case MONTH -> resolveMonth(chartOfAccountsId, period);
            case QUARTER -> resolveQuarter(chartOfAccountsId, period);
            case YEAR -> resolveYear(chartOfAccountsId, period);
            case YTD -> resolveYtd(chartOfAccountsId, period);
        };
    }

    private List<Long> resolveMonth(Long chartOfAccountsId, FinancialComparisonPeriod period) {
        FiscalPeriod fp = requireFiscalPeriod(chartOfAccountsId, period.getFiscalYear(),
                period.getPeriodNumber());
        return List.of(fp.getId());
    }

    private List<Long> resolveQuarter(Long chartOfAccountsId, FinancialComparisonPeriod period) {
        int startMonth = ((period.getPeriodNumber() - 1) * 3) + 1;
        List<Long> ids = new ArrayList<>(3);
        for (int month = startMonth; month < startMonth + 3; month++) {
            FiscalPeriod fp = requireFiscalPeriod(chartOfAccountsId, period.getFiscalYear(), month);
            ids.add(fp.getId());
        }
        return ids;
    }

    private List<Long> resolveYear(Long chartOfAccountsId, FinancialComparisonPeriod period) {
        List<Long> ids = new ArrayList<>(12);
        for (int month = 1; month <= 12; month++) {
            FiscalPeriod fp = requireFiscalPeriod(chartOfAccountsId, period.getFiscalYear(), month);
            ids.add(fp.getId());
        }
        return ids;
    }

    private List<Long> resolveYtd(Long chartOfAccountsId, FinancialComparisonPeriod period) {
        List<Long> ids = new ArrayList<>(period.getPeriodNumber());
        for (int month = 1; month <= period.getPeriodNumber(); month++) {
            FiscalPeriod fp = requireFiscalPeriod(chartOfAccountsId, period.getFiscalYear(), month);
            ids.add(fp.getId());
        }
        return ids;
    }

    private FiscalPeriod requireFiscalPeriod(Long chartOfAccountsId, Integer fiscalYear,
                                              Integer periodNumber) {
        return fiscalPeriodRepository
                .findByChartOfAccountsIdAndFiscalYearAndPeriodNumber(
                        chartOfAccountsId, fiscalYear, periodNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "FiscalPeriod not found for chartOfAccountsId=" + chartOfAccountsId
                                + ", fiscalYear=" + fiscalYear
                                + ", periodNumber=" + periodNumber));
    }
}
