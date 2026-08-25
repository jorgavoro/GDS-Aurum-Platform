package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReportLine;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportCalculator;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultAnalyticalReportingService implements AnalyticalReportingService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final AnalyticalReportCalculator calculator;

    public DefaultAnalyticalReportingService(LedgerEntryRepository ledgerEntryRepository,
                                             AnalyticalDimensionValueRepository dimensionValueRepository,
                                             FiscalPeriodRepository fiscalPeriodRepository,
                                             ChartOfAccountsRepository chartOfAccountsRepository,
                                             AnalyticalReportCalculator calculator) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.dimensionValueRepository = dimensionValueRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.calculator = calculator;
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyticalReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                     AnalyticalDimensionType dimensionType) {
        return generate(chartOfAccountsId, fiscalPeriodId, dimensionType, null);
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyticalReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                     AnalyticalDimensionType dimensionType, List<Long> dimensionIds) {
        validateInputs(chartOfAccountsId, fiscalPeriodId, dimensionType);

        chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + chartOfAccountsId));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "FiscalPeriod not found with ID: " + fiscalPeriodId));

        if (fiscalPeriod.getChartOfAccounts() == null
                || !chartOfAccountsId.equals(fiscalPeriod.getChartOfAccounts().getId())) {
            throw new IllegalArgumentException(
                    "FiscalPeriod does not belong to ChartOfAccounts: " + chartOfAccountsId);
        }

        List<AnalyticalDimensionValue> dimensions = dimensionValueRepository
                .findByDimensionTypeAndActive(dimensionType, Boolean.TRUE);

        if (dimensionIds != null && !dimensionIds.isEmpty()) {
            dimensions = dimensions.stream()
                    .filter(d -> dimensionIds.contains(d.getReferenceId()))
                    .toList();
        }

        Map<Long, List<LedgerEntry>> entriesByDimension = new LinkedHashMap<>();
        Map<Long, AnalyticalDimensionValue> dimensionById = new LinkedHashMap<>();

        for (AnalyticalDimensionValue dim : dimensions) {
            dimensionById.put(dim.getReferenceId(), dim);
            List<LedgerEntry> entries = ledgerEntryRepository
                    .findByDimensionTypeAndReferenceIdAndFiscalPeriodId(
                            dimensionType, dim.getReferenceId(), fiscalPeriodId);
            entriesByDimension.put(dim.getReferenceId(), entries);
        }

        List<AnalyticalReportLine> lines = new ArrayList<>();

        for (Map.Entry<Long, AnalyticalDimensionValue> entry : dimensionById.entrySet()) {
            Long refId = entry.getKey();
            AnalyticalDimensionValue dim = entry.getValue();
            List<LedgerEntry> entries = entriesByDimension.getOrDefault(refId, List.of());

            BigDecimal debit = calculator.calculateDebit(entries);
            BigDecimal credit = calculator.calculateCredit(entries);
            BigDecimal balance = calculator.calculateBalance(entries);
            BigDecimal revenue = calculator.calculateRevenue(entries);
            BigDecimal expense = calculator.calculateExpense(entries);
            BigDecimal netResult = calculator.calculateNetResult(entries);

            lines.add(new AnalyticalReportLine(
                    dimensionType, refId,
                    dim.getCode(), dim.getName(),
                    debit, credit, balance,
                    revenue, expense, netResult));
        }

        lines.sort(Comparator.comparing(AnalyticalReportLine::getDimensionCode));

        BigDecimal totalDebit = lines.stream()
                .map(AnalyticalReportLine::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = lines.stream()
                .map(AnalyticalReportLine::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRevenue = lines.stream()
                .map(AnalyticalReportLine::getRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpense = lines.stream()
                .map(AnalyticalReportLine::getExpense)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AnalyticalReport(
                chartOfAccountsId,
                fiscalPeriodId,
                fiscalPeriod.getName(),
                dimensionType,
                lines,
                totalDebit,
                totalCredit,
                totalDebit.subtract(totalCredit),
                totalRevenue,
                totalExpense,
                totalRevenue.subtract(totalExpense));
    }

    private void validateInputs(Long chartOfAccountsId, Long fiscalPeriodId,
                                 AnalyticalDimensionType dimensionType) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }
        if (dimensionType == null) {
            throw new IllegalArgumentException("dimensionType cannot be null");
        }
    }
}
