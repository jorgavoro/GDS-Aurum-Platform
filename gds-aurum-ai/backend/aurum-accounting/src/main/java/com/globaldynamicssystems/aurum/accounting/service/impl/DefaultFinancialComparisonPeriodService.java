package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonPeriodType;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonPeriodService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultFinancialComparisonPeriodService implements FinancialComparisonPeriodService {

    private final FiscalPeriodRepository fiscalPeriodRepository;

    public DefaultFinancialComparisonPeriodService(FiscalPeriodRepository fiscalPeriodRepository) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
    }

    @Override
    public FinancialComparisonPeriod resolveCurrentPeriod(Integer fiscalYear,
                                                          Integer periodNumber,
                                                          FinancialComparisonPeriodType type) {
        return resolve(fiscalYear, periodNumber, type);
    }

    @Override
    public FinancialComparisonPeriod resolvePreviousPeriod(Integer fiscalYear,
                                                           Integer periodNumber,
                                                           FinancialComparisonPeriodType type) {
        return resolve(fiscalYear - 1, periodNumber, type);
    }

    private FinancialComparisonPeriod resolve(Integer fiscalYear,
                                              Integer periodNumber,
                                              FinancialComparisonPeriodType type) {
        switch (type) {
            case MONTH -> {
                return resolveMonth(fiscalYear, periodNumber);
            }
            case QUARTER -> {
                return resolveQuarter(fiscalYear, periodNumber);
            }
            case YEAR -> {
                return resolveYear(fiscalYear);
            }
            case YTD -> {
                return resolveYtd(fiscalYear, periodNumber);
            }
            default -> throw new IllegalArgumentException("Unsupported period type: " + type);
        }
    }

    private FinancialComparisonPeriod resolveMonth(Integer fiscalYear, Integer periodNumber) {
        String label = String.format("%d-%02d", fiscalYear, periodNumber);
        // Use any chartOfAccounts to get date range — we only need the label and dates for metadata.
        // Dates are derived from the period number (month) directly for MVP 1 (12-period calendar).
        LocalDate startDate = LocalDate.of(fiscalYear, periodNumber, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return new FinancialComparisonPeriod(FinancialComparisonPeriodType.MONTH,
                fiscalYear, periodNumber, startDate, endDate, label);
    }

    private FinancialComparisonPeriod resolveQuarter(Integer fiscalYear, Integer quarterNumber) {
        int startMonth = ((quarterNumber - 1) * 3) + 1;
        int endMonth = startMonth + 2;
        LocalDate startDate = LocalDate.of(fiscalYear, startMonth, 1);
        LocalDate endDate = LocalDate.of(fiscalYear, endMonth, 1)
                .withDayOfMonth(LocalDate.of(fiscalYear, endMonth, 1).lengthOfMonth());
        String label = String.format("Q%d-%d", quarterNumber, fiscalYear);
        return new FinancialComparisonPeriod(FinancialComparisonPeriodType.QUARTER,
                fiscalYear, quarterNumber, startDate, endDate, label);
    }

    private FinancialComparisonPeriod resolveYear(Integer fiscalYear) {
        LocalDate startDate = LocalDate.of(fiscalYear, 1, 1);
        LocalDate endDate = LocalDate.of(fiscalYear, 12, 31);
        String label = String.format("FY-%d", fiscalYear);
        return new FinancialComparisonPeriod(FinancialComparisonPeriodType.YEAR,
                fiscalYear, 1, startDate, endDate, label);
    }

    private FinancialComparisonPeriod resolveYtd(Integer fiscalYear, Integer periodNumber) {
        LocalDate startDate = LocalDate.of(fiscalYear, 1, 1);
        LocalDate endDate = LocalDate.of(fiscalYear, periodNumber, 1)
                .withDayOfMonth(LocalDate.of(fiscalYear, periodNumber, 1).lengthOfMonth());
        String label = String.format("YTD-%d-%02d", fiscalYear, periodNumber);
        return new FinancialComparisonPeriod(FinancialComparisonPeriodType.YTD,
                fiscalYear, periodNumber, startDate, endDate, label);
    }
}
