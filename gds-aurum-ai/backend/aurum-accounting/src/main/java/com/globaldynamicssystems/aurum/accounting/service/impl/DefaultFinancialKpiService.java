package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpi;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiDefinition;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisService;
import com.globaldynamicssystems.aurum.accounting.service.AccountingDataQualityService;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportingService;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiCatalog;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiValidator;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultFinancialKpiService implements FinancialKpiService {

    private final AnalyticalReportingService analyticalReportingService;
    private final ProfitabilityService profitabilityService;
    private final AccountFinancialAnalysisService accountFinancialAnalysisService;
    private final FinancialRatioService financialRatioService;
    private final CashFlowService cashFlowService;
    private final AccountingDataQualityService accountingDataQualityService;
    private final FinancialKpiCatalog kpiCatalog;
    private final FinancialKpiValidator kpiValidator;

    public DefaultFinancialKpiService(
            AnalyticalReportingService analyticalReportingService,
            ProfitabilityService profitabilityService,
            AccountFinancialAnalysisService accountFinancialAnalysisService,
            FinancialRatioService financialRatioService,
            CashFlowService cashFlowService,
            AccountingDataQualityService accountingDataQualityService,
            FinancialKpiCatalog kpiCatalog,
            FinancialKpiValidator kpiValidator) {
        this.analyticalReportingService = analyticalReportingService;
        this.profitabilityService = profitabilityService;
        this.accountFinancialAnalysisService = accountFinancialAnalysisService;
        this.financialRatioService = financialRatioService;
        this.cashFlowService = cashFlowService;
        this.accountingDataQualityService = accountingDataQualityService;
        this.kpiCatalog = kpiCatalog;
        this.kpiValidator = kpiValidator;
    }

    @Override
    public FinancialKpiReport calculate(Long chartOfAccountsId, Long fiscalPeriodId) {
        return calculate(new FinancialKpiFilter(chartOfAccountsId, fiscalPeriodId));
    }

    @Override
    public FinancialKpiReport calculate(FinancialKpiFilter filter) {
        if (filter == null || filter.getChartOfAccountsId() == null || filter.getFiscalPeriodId() == null) {
            throw new IllegalArgumentException("ChartOfAccountsId and FiscalPeriodId are required.");
        }

        Long coaId = filter.getChartOfAccountsId();
        Long periodId = filter.getFiscalPeriodId();
        Set<FinancialKpiType> requestedTypes = resolveRequestedTypes(filter.getKpiTypes());

        AccountingDataQualityReport qualityReport = accountingDataQualityService.validate(coaId, periodId);
        boolean isValid = determineFinancialValidity(qualityReport);
        String periodName = (qualityReport != null) ? qualityReport.getFiscalPeriodName() : null;

        ProfitabilityReport profitReport = requiresProfitabilityReport(requestedTypes)
                ? profitabilityService.generate(coaId, periodId, (AnalyticalDimensionType) null)
                : null;

        CashFlowReport cashFlowReport = requiresCashFlowReport(requestedTypes)
                ? cashFlowService.generate(coaId, periodId)
                : null;

        FinancialRatioReport ratioReport = requiresRatioReport(requestedTypes)
                ? financialRatioService.calculate(coaId, periodId)
                : null;
        

        List<FinancialKpi> kpiList = new ArrayList<>();

        for (FinancialKpiType type : requestedTypes) {
            BigDecimal value = extractKpiValue(type, profitReport, cashFlowReport, ratioReport, qualityReport);
            FinancialKpiDefinition def = kpiCatalog.find(type)
                    .orElseThrow(() -> new IllegalStateException("Definition missing for KPI: " + type));

            kpiList.add(new FinancialKpi(
                    def.getType(),
                    def.getCode(),
                    def.getName(),
                    value != null ? value : BigDecimal.ZERO,
                    def.getUnit(),
                    def.getCategory(),
                    def.getDescription()
            ));
        }

        FinancialKpiReport report = new FinancialKpiReport(
                coaId,
                periodId,
                periodName,
                kpiList,
                isValid
        );

        kpiValidator.validate(report);
        return report;
    }

    private Set<FinancialKpiType> resolveRequestedTypes(List<FinancialKpiType> filterTypes) {
        return (filterTypes == null || filterTypes.isEmpty())
                ? EnumSet.allOf(FinancialKpiType.class)
                : EnumSet.copyOf(filterTypes);
    }

    private boolean determineFinancialValidity(AccountingDataQualityReport qualityReport) {
        if (qualityReport == null) {
            return true;
        }
        if (qualityReport.getValid() != null && !qualityReport.getValid()) {
            return false;
        }
        return qualityReport.getErrorCount() == null || qualityReport.getErrorCount() == 0;
    }

    private boolean requiresProfitabilityReport(Set<FinancialKpiType> types) {
        return types.contains(FinancialKpiType.REVENUE)
                || types.contains(FinancialKpiType.EXPENSE)
                || types.contains(FinancialKpiType.NET_RESULT)
                || types.contains(FinancialKpiType.NET_MARGIN);
    }

    private boolean requiresCashFlowReport(Set<FinancialKpiType> types) {
        return types.contains(FinancialKpiType.OPERATING_CASH_FLOW)
                || types.contains(FinancialKpiType.INVESTING_CASH_FLOW)
                || types.contains(FinancialKpiType.FINANCING_CASH_FLOW)
                || types.contains(FinancialKpiType.NET_CASH_FLOW);
    }

    private boolean requiresRatioReport(Set<FinancialKpiType> types) {
        return types.contains(FinancialKpiType.CURRENT_RATIO)
                || types.contains(FinancialKpiType.QUICK_RATIO)
                || types.contains(FinancialKpiType.DEBT_RATIO)
                || types.contains(FinancialKpiType.DEBT_TO_EQUITY)
                || types.contains(FinancialKpiType.RETURN_ON_ASSETS)
                || types.contains(FinancialKpiType.RETURN_ON_EQUITY)
                || types.contains(FinancialKpiType.GROSS_MARGIN);
    }

    private BigDecimal extractKpiValue(
            FinancialKpiType type,
            ProfitabilityReport profit,
            CashFlowReport cashFlow,
            FinancialRatioReport ratioReport,
            AccountingDataQualityReport quality) {

        switch (type) {
            case REVENUE:
                return (profit != null) ? profit.getTotalRevenue() : BigDecimal.ZERO;
            case EXPENSE:
                return (profit != null) ? profit.getTotalExpense() : BigDecimal.ZERO;
            case NET_RESULT:
                return (profit != null) ? profit.getTotalNetResult() : BigDecimal.ZERO;
            case NET_MARGIN:
                return (profit != null) ? profit.getTotalMargin() : BigDecimal.ZERO;

            case OPERATING_CASH_FLOW:
                return (cashFlow != null) ? cashFlow.getOperatingCashFlow() : BigDecimal.ZERO;
            case INVESTING_CASH_FLOW:
                return (cashFlow != null) ? cashFlow.getInvestingCashFlow() : BigDecimal.ZERO;
            case FINANCING_CASH_FLOW:
                return (cashFlow != null) ? cashFlow.getFinancingCashFlow() : BigDecimal.ZERO;
            case NET_CASH_FLOW:
                return (cashFlow != null) ? cashFlow.getNetCashFlow() : BigDecimal.ZERO;

            case CURRENT_RATIO:
                return findRatioValue(ratioReport, "CURRENT_RATIO");
            case QUICK_RATIO:
                return findRatioValue(ratioReport, "QUICK_RATIO");
            case DEBT_RATIO:
                return findRatioValue(ratioReport, "DEBT_RATIO");
            case DEBT_TO_EQUITY:
                return findRatioValue(ratioReport, "DEBT_TO_EQUITY");
            case RETURN_ON_ASSETS:
                return findRatioValue(ratioReport, "RETURN_ON_ASSETS");
            case RETURN_ON_EQUITY:
                return findRatioValue(ratioReport, "RETURN_ON_EQUITY");
            case GROSS_MARGIN:
                return findRatioValue(ratioReport, "GROSS_MARGIN");

            case DATA_QUALITY_ERROR_COUNT:
                return (quality != null && quality.getErrorCount() != null)
                        ? BigDecimal.valueOf(quality.getErrorCount())
                        : BigDecimal.ZERO;
            case DATA_QUALITY_WARNING_COUNT:
                return (quality != null && quality.getWarningCount() != null)
                        ? BigDecimal.valueOf(quality.getWarningCount())
                        : BigDecimal.ZERO;

            default:
                return BigDecimal.ZERO;
        }
    }

    private BigDecimal findRatioValue(FinancialRatioReport ratioReport, String identifier) {
        if (ratioReport == null || ratioReport.getRatios() == null) {
            return BigDecimal.ZERO;
        }

        return ratioReport.getRatios().stream()
                .filter(r -> r != null && (
                        identifier.equalsIgnoreCase(r.getCode()) ||
                        identifier.equalsIgnoreCase(r.getName()) ||
                        (r.getType() != null && identifier.equalsIgnoreCase(r.getType().name()))
                ))
                .map(FinancialRatio::getValue)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}