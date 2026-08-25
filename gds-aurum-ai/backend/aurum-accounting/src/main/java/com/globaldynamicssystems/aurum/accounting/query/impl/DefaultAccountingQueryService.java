package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysis;
import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiFilter;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryException;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryRequest;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryService;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryValidator;
import com.globaldynamicssystems.aurum.accounting.query.CashFlowQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.ComparisonQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.DataQualityQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.KpiQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.ProfitabilityQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.RatioQueryResult;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisService;
import com.globaldynamicssystems.aurum.accounting.service.AccountingDataQualityService;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAccountingQueryService implements AccountingQueryService {

    private final AccountFinancialAnalysisService accountFinancialAnalysisService;
    private final ProfitabilityService profitabilityService;
    private final FinancialRatioService financialRatioService;
    private final CashFlowService cashFlowService;
    private final FinancialKpiService financialKpiService;
    private final AccountingDataQualityService accountingDataQualityService;
    private final FinancialComparisonService financialComparisonService;
    private final AccountingQueryValidator validator;

    public DefaultAccountingQueryService(
            AccountFinancialAnalysisService accountFinancialAnalysisService,
            ProfitabilityService profitabilityService,
            FinancialRatioService financialRatioService,
            CashFlowService cashFlowService,
            FinancialKpiService financialKpiService,
            AccountingDataQualityService accountingDataQualityService,
            FinancialComparisonService financialComparisonService,
            AccountingQueryValidator validator) {
        this.accountFinancialAnalysisService = accountFinancialAnalysisService;
        this.profitabilityService = profitabilityService;
        this.financialRatioService = financialRatioService;
        this.cashFlowService = cashFlowService;
        this.financialKpiService = financialKpiService;
        this.accountingDataQualityService = accountingDataQualityService;
        this.financialComparisonService = financialComparisonService;
        this.validator = validator;
    }

    @Override
    public AccountingQueryResult execute(AccountingQueryRequest request) {
        validator.validate(request);

        try {
            switch (request.getQueryType()) {
                case ACCOUNT:
                    return handleAccountQuery(request);
                case PROFITABILITY:
                    return handleProfitabilityQuery(request);
                case RATIO:
                    return handleRatioQuery(request);
                case CASH_FLOW:
                    return handleCashFlowQuery(request);
                case KPI:
                    return handleKpiQuery(request);
                case DATA_QUALITY:
                    return handleDataQualityQuery(request);
                case COMPARISON:
                    return handleComparisonQuery(request);
                default:
                    throw new AccountingQueryException("Unsupported query type: " + request.getQueryType());
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new AccountingQueryException("An error occurred executing query of type " + request.getQueryType(), e);
        }
    }

    private AccountQueryResult handleAccountQuery(AccountingQueryRequest request) {
        List<AccountFinancialAnalysis> accounts = accountFinancialAnalysisService.analyze(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                request.getAccountIds()
        );

        return new AccountQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                accounts,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }

    private ProfitabilityQueryResult handleProfitabilityQuery(AccountingQueryRequest request) {
        ProfitabilityReport report = profitabilityService.generate(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                request.getDimensionType()
        );
        return new ProfitabilityQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                report
        );
    }

    private RatioQueryResult handleRatioQuery(AccountingQueryRequest request) {
        FinancialRatioReport report = financialRatioService.calculate(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId()
        );

        List<FinancialRatio> ratios = (report != null && report.getRatios() != null) ? report.getRatios() : new ArrayList<>();

        if (request.getRatioTypes() != null && !request.getRatioTypes().isEmpty()) {
            ratios = ratios.stream()
                    .filter(r -> r.getType() != null && request.getRatioTypes().contains(r.getType()))
                    .collect(Collectors.toList());
        }

        return new RatioQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                ratios
        );
    }

    private CashFlowQueryResult handleCashFlowQuery(AccountingQueryRequest request) {
        CashFlowReport report = cashFlowService.calculate(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId()
        );
        return new CashFlowQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                report
        );
    }

    private KpiQueryResult handleKpiQuery(AccountingQueryRequest request) {
        FinancialKpiFilter filter = new FinancialKpiFilter(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId()
        );
        
        if (request.getKpiTypes() != null && !request.getKpiTypes().isEmpty()) {
            filter.setKpiTypes(request.getKpiTypes());
        }

        FinancialKpiReport report = financialKpiService.calculate(filter);
        
        return new KpiQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                report != null ? report.getKpis() : new ArrayList<>(),
                report != null ? report.getValid() : false
        );
    }

    private DataQualityQueryResult handleDataQualityQuery(AccountingQueryRequest request) {
        AccountingDataQualityReport report = accountingDataQualityService.evaluate(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId()
        );

        return new DataQualityQueryResult(
                request.getChartOfAccountsId(),
                request.getFiscalPeriodId(),
                report != null ? report.getFindings() : new ArrayList<>(),
                report != null && report.getErrorCount() != null ? report.getErrorCount() : 0,
                report != null && report.getWarningCount() != null ? report.getWarningCount() : 0,
                report != null && report.getInfoCount() != null ? report.getInfoCount() : 0,
                report != null && report.getValid() != null ? report.getValid() : false
        );
    }

    private ComparisonQueryResult handleComparisonQuery(AccountingQueryRequest request) {
        FinancialComparisonReport report = financialComparisonService.compare(
                request.getChartOfAccountsId(),
                request.getCurrentFiscalPeriodId(),
                request.getPreviousFiscalPeriodId(),
                request.getDimensionType()
        );
        return new ComparisonQueryResult(
                request.getChartOfAccountsId(),
                request.getCurrentFiscalPeriodId(),
                request.getPreviousFiscalPeriodId(),
                report
        );
    }
}