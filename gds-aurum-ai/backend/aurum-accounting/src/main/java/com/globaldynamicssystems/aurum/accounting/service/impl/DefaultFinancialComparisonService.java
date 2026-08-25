package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonLine;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityLine;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonCalculator;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonValidator;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityCalculator;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultFinancialComparisonService implements FinancialComparisonService {

    private final ProfitabilityService profitabilityService;
    private final ProfitabilityCalculator profitabilityCalculator;
    private final FinancialComparisonCalculator calculator;
    private final FinancialComparisonValidator validator;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public DefaultFinancialComparisonService(ProfitabilityService profitabilityService,
                                             ProfitabilityCalculator profitabilityCalculator,
                                             FinancialComparisonCalculator calculator,
                                             FinancialComparisonValidator validator,
                                             FiscalPeriodRepository fiscalPeriodRepository,
                                             ChartOfAccountsRepository chartOfAccountsRepository) {
        this.profitabilityService = profitabilityService;
        this.profitabilityCalculator = profitabilityCalculator;
        this.calculator = calculator;
        this.validator = validator;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialComparisonReport compare(Long chartOfAccountsId,
                                             Long currentFiscalPeriodId,
                                             Long previousFiscalPeriodId,
                                             AnalyticalDimensionType dimensionType) {
        return compare(chartOfAccountsId, currentFiscalPeriodId, previousFiscalPeriodId,
                dimensionType, null);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialComparisonReport compare(Long chartOfAccountsId,
                                             Long currentFiscalPeriodId,
                                             Long previousFiscalPeriodId,
                                             AnalyticalDimensionType dimensionType,
                                             List<Long> dimensionIds) {
        if (chartOfAccountsId == null) throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        if (currentFiscalPeriodId == null) throw new IllegalArgumentException("currentFiscalPeriodId cannot be null");
        if (previousFiscalPeriodId == null) throw new IllegalArgumentException("previousFiscalPeriodId cannot be null");
        if (dimensionType == null) throw new IllegalArgumentException("dimensionType cannot be null");
        if (currentFiscalPeriodId.equals(previousFiscalPeriodId)) {
            throw new IllegalArgumentException("currentFiscalPeriodId and previousFiscalPeriodId must be different");
        }

        chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + chartOfAccountsId));

        FiscalPeriod currentPeriod = requireFiscalPeriod(currentFiscalPeriodId, "Current");
        FiscalPeriod previousPeriod = requireFiscalPeriod(previousFiscalPeriodId, "Previous");

        requireSameChartOfAccounts(currentPeriod, chartOfAccountsId, "Current");
        requireSameChartOfAccounts(previousPeriod, chartOfAccountsId, "Previous");

        ProfitabilityReport currentReport = profitabilityService.generate(
                chartOfAccountsId, currentFiscalPeriodId, dimensionType, dimensionIds);
        ProfitabilityReport previousReport = profitabilityService.generate(
                chartOfAccountsId, previousFiscalPeriodId, dimensionType, dimensionIds);

        return buildReport(chartOfAccountsId, dimensionType,
                currentPeriod.getId(), previousPeriod.getId(),
                currentPeriod.getName(), previousPeriod.getName(),
                currentReport, previousReport);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialComparisonReport compare(Long chartOfAccountsId,
                                             List<Long> currentFiscalPeriodIds,
                                             List<Long> previousFiscalPeriodIds,
                                             String currentPeriodLabel,
                                             String previousPeriodLabel,
                                             AnalyticalDimensionType dimensionType,
                                             List<Long> dimensionIds) {
        if (chartOfAccountsId == null) throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        if (currentFiscalPeriodIds == null || currentFiscalPeriodIds.isEmpty()) {
            throw new IllegalArgumentException("currentFiscalPeriodIds cannot be null or empty");
        }
        if (previousFiscalPeriodIds == null || previousFiscalPeriodIds.isEmpty()) {
            throw new IllegalArgumentException("previousFiscalPeriodIds cannot be null or empty");
        }
        if (dimensionType == null) throw new IllegalArgumentException("dimensionType cannot be null");

        chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + chartOfAccountsId));

        ProfitabilityReport currentReport = aggregateProfitability(
                chartOfAccountsId, currentFiscalPeriodIds, currentPeriodLabel, dimensionType, dimensionIds);
        ProfitabilityReport previousReport = aggregateProfitability(
                chartOfAccountsId, previousFiscalPeriodIds, previousPeriodLabel, dimensionType, dimensionIds);

        return buildReport(chartOfAccountsId, dimensionType,
                null, null,
                currentPeriodLabel, previousPeriodLabel,
                currentReport, previousReport);
    }

    private ProfitabilityReport aggregateProfitability(Long chartOfAccountsId,
                                                        List<Long> fiscalPeriodIds,
                                                        String label,
                                                        AnalyticalDimensionType dimensionType,
                                                        List<Long> dimensionIds) {
        Map<Long, BigDecimal[]> aggregated = new LinkedHashMap<>();
        Map<Long, String[]> dimMeta = new LinkedHashMap<>();

        for (Long periodId : fiscalPeriodIds) {
            ProfitabilityReport report = profitabilityService.generate(
                    chartOfAccountsId, periodId, dimensionType, dimensionIds);
            for (ProfitabilityLine line : report.getLines()) {
                Long dimId = line.getDimensionId();
                dimMeta.putIfAbsent(dimId, new String[]{line.getDimensionCode(), line.getDimensionName()});
                BigDecimal[] totals = aggregated.computeIfAbsent(dimId,
                        k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
                totals[0] = totals[0].add(line.getRevenue());
                totals[1] = totals[1].add(line.getExpense());
            }
        }

        List<ProfitabilityLine> lines = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Map.Entry<Long, BigDecimal[]> entry : aggregated.entrySet()) {
            Long dimId = entry.getKey();
            BigDecimal revenue = entry.getValue()[0];
            BigDecimal expense = entry.getValue()[1];
            BigDecimal netResult = profitabilityCalculator.calculateNetResult(revenue, expense);
            BigDecimal marginPct = profitabilityCalculator.calculateMarginPercentage(revenue, netResult);
            String[] meta = dimMeta.get(dimId);
            lines.add(new ProfitabilityLine(dimensionType, dimId, meta[0], meta[1],
                    revenue, expense, netResult, netResult, marginPct));
            totalRevenue = totalRevenue.add(revenue);
            totalExpense = totalExpense.add(expense);
        }

        BigDecimal totalNetResult = profitabilityCalculator.calculateNetResult(totalRevenue, totalExpense);
        BigDecimal totalMarginPct = profitabilityCalculator.calculateMarginPercentage(totalRevenue, totalNetResult);

        return new ProfitabilityReport(chartOfAccountsId, null, label, dimensionType, lines,
                totalRevenue, totalExpense, totalNetResult, totalNetResult, totalMarginPct);
    }

    private FinancialComparisonReport buildReport(Long chartOfAccountsId,
                                                   AnalyticalDimensionType dimensionType,
                                                   Long currentPeriodId,
                                                   Long previousPeriodId,
                                                   String currentLabel,
                                                   String previousLabel,
                                                   ProfitabilityReport currentReport,
                                                   ProfitabilityReport previousReport) {
        Map<Long, ProfitabilityLine> currentByDimId = indexById(currentReport.getLines());
        Map<Long, ProfitabilityLine> previousByDimId = indexById(previousReport.getLines());

        Map<Long, String[]> allDimensions = new LinkedHashMap<>();
        for (ProfitabilityLine line : currentReport.getLines()) {
            allDimensions.put(line.getDimensionId(),
                    new String[]{line.getDimensionCode(), line.getDimensionName()});
        }
        for (ProfitabilityLine line : previousReport.getLines()) {
            allDimensions.putIfAbsent(line.getDimensionId(),
                    new String[]{line.getDimensionCode(), line.getDimensionName()});
        }

        List<FinancialComparisonLine> lines = new ArrayList<>();

        for (Map.Entry<Long, String[]> entry : allDimensions.entrySet()) {
            Long dimId = entry.getKey();
            String dimCode = entry.getValue()[0];
            String dimName = entry.getValue()[1];

            ProfitabilityLine cur = currentByDimId.getOrDefault(dimId, zeroProfitabilityLine());
            ProfitabilityLine prev = previousByDimId.getOrDefault(dimId, zeroProfitabilityLine());

            lines.add(new FinancialComparisonLine(
                    dimensionType, dimId, dimCode, dimName,
                    cur.getRevenue(), prev.getRevenue(),
                    calculator.calculateVariation(cur.getRevenue(), prev.getRevenue()),
                    calculator.calculateVariationPercentage(cur.getRevenue(), prev.getRevenue()),
                    cur.getExpense(), prev.getExpense(),
                    calculator.calculateVariation(cur.getExpense(), prev.getExpense()),
                    calculator.calculateVariationPercentage(cur.getExpense(), prev.getExpense()),
                    cur.getNetResult(), prev.getNetResult(),
                    calculator.calculateVariation(cur.getNetResult(), prev.getNetResult()),
                    calculator.calculateVariationPercentage(cur.getNetResult(), prev.getNetResult()),
                    cur.getMarginPercentage(), prev.getMarginPercentage(),
                    calculator.calculateVariation(cur.getMarginPercentage(), prev.getMarginPercentage())));
        }

        lines.sort(Comparator.comparing(FinancialComparisonLine::getDimensionCode));

        BigDecimal totalCurRevenue = currentReport.getTotalRevenue();
        BigDecimal totalPrevRevenue = previousReport.getTotalRevenue();
        BigDecimal totalCurExpense = currentReport.getTotalExpense();
        BigDecimal totalPrevExpense = previousReport.getTotalExpense();
        BigDecimal totalCurNetResult = currentReport.getTotalNetResult();
        BigDecimal totalPrevNetResult = previousReport.getTotalNetResult();
        BigDecimal totalCurMarginPct = currentReport.getTotalMarginPercentage();
        BigDecimal totalPrevMarginPct = previousReport.getTotalMarginPercentage();

        FinancialComparisonReport report = new FinancialComparisonReport(
                chartOfAccountsId,
                currentPeriodId, previousPeriodId,
                currentLabel, previousLabel,
                dimensionType, lines,
                totalCurRevenue, totalPrevRevenue,
                calculator.calculateVariation(totalCurRevenue, totalPrevRevenue),
                totalCurExpense, totalPrevExpense,
                calculator.calculateVariation(totalCurExpense, totalPrevExpense),
                totalCurNetResult, totalPrevNetResult,
                calculator.calculateVariation(totalCurNetResult, totalPrevNetResult),
                totalCurMarginPct, totalPrevMarginPct,
                calculator.calculateVariation(totalCurMarginPct, totalPrevMarginPct));

        validator.validate(report);

        return report;
    }

    private FiscalPeriod requireFiscalPeriod(Long fiscalPeriodId, String label) {
        return fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new IllegalArgumentException(
                        label + " FiscalPeriod not found with ID: " + fiscalPeriodId));
    }

    private void requireSameChartOfAccounts(FiscalPeriod period, Long chartOfAccountsId, String label) {
        if (period.getChartOfAccounts() == null
                || !chartOfAccountsId.equals(period.getChartOfAccounts().getId())) {
            throw new IllegalArgumentException(
                    label + " FiscalPeriod does not belong to ChartOfAccounts: " + chartOfAccountsId);
        }
    }

    private Map<Long, ProfitabilityLine> indexById(List<ProfitabilityLine> lines) {
        Map<Long, ProfitabilityLine> map = new LinkedHashMap<>();
        if (lines != null) {
            for (ProfitabilityLine line : lines) {
                map.put(line.getDimensionId(), line);
            }
        }
        return map;
    }

    private ProfitabilityLine zeroProfitabilityLine() {
        return new ProfitabilityLine(null, null, null, null,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
