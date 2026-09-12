package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysis;
import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisReport;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioInput;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioType;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioCalculator;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultFinancialRatioService implements FinancialRatioService {

    private static final String UNIT_RATIO = "RATIO";
    private static final String UNIT_PERCENTAGE = "PERCENTAGE";

    private final AccountFinancialAnalysisService analysisService;
    private final FinancialRatioCalculator calculator;
    private final FinancialRatioValidator validator;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public DefaultFinancialRatioService(AccountFinancialAnalysisService analysisService,
                                        FinancialRatioCalculator calculator,
                                        FinancialRatioValidator validator,
                                        FiscalPeriodRepository fiscalPeriodRepository,
                                        ChartOfAccountsRepository chartOfAccountsRepository) {
        this.analysisService = analysisService;
        this.calculator = calculator;
        this.validator = validator;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialRatioReport calculate(Long chartOfAccountsId, Long fiscalPeriodId) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }

        chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + chartOfAccountsId));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "FiscalPeriod not found with ID: " + fiscalPeriodId));

        AccountFinancialAnalysisReport analysisReport =
                analysisService.analyze(chartOfAccountsId, fiscalPeriodId);

        FinancialRatioInput input = buildInput(analysisReport);

        List<FinancialRatio> ratios = buildAllRatios(input);

        FinancialRatioReport report = new FinancialRatioReport(
                chartOfAccountsId,
                fiscalPeriodId,
                fiscalPeriod.getName(),
                ratios);

        validator.validate(report);

        return report;
    }

    private FinancialRatioInput buildInput(AccountFinancialAnalysisReport report) {
        BigDecimal totalAssets = sumByType(report, AccountType.ASSET);
        BigDecimal totalLiabilities = sumByType(report, AccountType.LIABILITY);
        BigDecimal totalEquity = sumByType(report, AccountType.EQUITY);
        BigDecimal revenue = report.getTotalRevenue();
        BigDecimal expense = report.getTotalExpense();
        BigDecimal netIncome = revenue.subtract(expense);

        // MVP: no sub-classification for current assets/liabilities or inventory
        // currentAssets = totalAssets, currentLiabilities = totalLiabilities, inventory = 0
        // costOfRevenue = expense (best available approximation in MVP)
        return new FinancialRatioInput(
                totalAssets,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                totalAssets,
                totalLiabilities,
                totalEquity,
                revenue,
                expense,
                netIncome);
    }

    private BigDecimal sumByType(AccountFinancialAnalysisReport report, AccountType type) {
        if (report.getAccounts() == null) {
            return BigDecimal.ZERO;
        }
        return report.getAccounts().stream()
                .filter(a -> type.equals(a.getAccountType()))
                .map(AccountFinancialAnalysis::getBalance)
                .map(b -> b != null ? b : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<FinancialRatio> buildAllRatios(FinancialRatioInput input) {
        List<FinancialRatio> ratios = new ArrayList<>();

        ratios.add(new FinancialRatio(
                FinancialRatioType.CURRENT_RATIO,
                "CURRENT_RATIO",
                "Current Ratio",
                calculator.calculateCurrentRatio(input.getCurrentAssets(), input.getTotalLiabilities()),
                UNIT_RATIO,
                "Current Assets / Current Liabilities"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.QUICK_RATIO,
                "QUICK_RATIO",
                "Quick Ratio",
                calculator.calculateQuickRatio(input.getCurrentAssets(), input.getInventory(),
                        input.getTotalLiabilities()),
                UNIT_RATIO,
                "(Current Assets - Inventory) / Current Liabilities"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.DEBT_RATIO,
                "DEBT_RATIO",
                "Debt Ratio",
                calculator.calculateDebtRatio(input.getTotalLiabilities(), input.getTotalAssets()),
                UNIT_PERCENTAGE,
                "Total Liabilities / Total Assets"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.DEBT_TO_EQUITY,
                "DEBT_TO_EQUITY",
                "Debt to Equity",
                calculator.calculateDebtToEquity(input.getTotalLiabilities(), input.getTotalEquity()),
                UNIT_RATIO,
                "Total Liabilities / Total Equity"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.GROSS_MARGIN,
                "GROSS_MARGIN",
                "Gross Margin",
                calculator.calculateGrossMargin(input.getRevenue(), input.getCostOfRevenue()),
                UNIT_PERCENTAGE,
                "(Revenue - Cost of Revenue) / Revenue * 100"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.NET_PROFIT_MARGIN,
                "NET_PROFIT_MARGIN",
                "Net Profit Margin",
                calculator.calculateNetProfitMargin(input.getNetIncome(), input.getRevenue()),
                UNIT_PERCENTAGE,
                "Net Income / Revenue * 100"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.RETURN_ON_ASSETS,
                "RETURN_ON_ASSETS",
                "Return on Assets",
                calculator.calculateReturnOnAssets(input.getNetIncome(), input.getTotalAssets()),
                UNIT_PERCENTAGE,
                "Net Income / Total Assets * 100"));

        ratios.add(new FinancialRatio(
                FinancialRatioType.RETURN_ON_EQUITY,
                "RETURN_ON_EQUITY",
                "Return on Equity",
                calculator.calculateReturnOnEquity(input.getNetIncome(), input.getTotalEquity()),
                UNIT_PERCENTAGE,
                "Net Income / Total Equity * 100"));

        return ratios;
    }
}
