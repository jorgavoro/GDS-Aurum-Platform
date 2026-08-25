package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class FinancialComparisonReport {

    private Long chartOfAccountsId;
    private Long currentFiscalPeriodId;
    private Long previousFiscalPeriodId;
    private String currentFiscalPeriodName;
    private String previousFiscalPeriodName;
    private AnalyticalDimensionType dimensionType;
    private List<FinancialComparisonLine> lines;

    private BigDecimal totalCurrentRevenue;
    private BigDecimal totalPreviousRevenue;
    private BigDecimal totalRevenueVariation;

    private BigDecimal totalCurrentExpense;
    private BigDecimal totalPreviousExpense;
    private BigDecimal totalExpenseVariation;

    private BigDecimal totalCurrentNetResult;
    private BigDecimal totalPreviousNetResult;
    private BigDecimal totalNetResultVariation;

    private BigDecimal totalCurrentMarginPercentage;
    private BigDecimal totalPreviousMarginPercentage;
    private BigDecimal totalMarginPercentageVariation;

    public FinancialComparisonReport() {
    }

    public FinancialComparisonReport(Long chartOfAccountsId,
                                     Long currentFiscalPeriodId, Long previousFiscalPeriodId,
                                     String currentFiscalPeriodName, String previousFiscalPeriodName,
                                     AnalyticalDimensionType dimensionType,
                                     List<FinancialComparisonLine> lines,
                                     BigDecimal totalCurrentRevenue, BigDecimal totalPreviousRevenue,
                                     BigDecimal totalRevenueVariation,
                                     BigDecimal totalCurrentExpense, BigDecimal totalPreviousExpense,
                                     BigDecimal totalExpenseVariation,
                                     BigDecimal totalCurrentNetResult, BigDecimal totalPreviousNetResult,
                                     BigDecimal totalNetResultVariation,
                                     BigDecimal totalCurrentMarginPercentage,
                                     BigDecimal totalPreviousMarginPercentage,
                                     BigDecimal totalMarginPercentageVariation) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.currentFiscalPeriodId = currentFiscalPeriodId;
        this.previousFiscalPeriodId = previousFiscalPeriodId;
        this.currentFiscalPeriodName = currentFiscalPeriodName;
        this.previousFiscalPeriodName = previousFiscalPeriodName;
        this.dimensionType = dimensionType;
        this.lines = lines;
        this.totalCurrentRevenue = totalCurrentRevenue;
        this.totalPreviousRevenue = totalPreviousRevenue;
        this.totalRevenueVariation = totalRevenueVariation;
        this.totalCurrentExpense = totalCurrentExpense;
        this.totalPreviousExpense = totalPreviousExpense;
        this.totalExpenseVariation = totalExpenseVariation;
        this.totalCurrentNetResult = totalCurrentNetResult;
        this.totalPreviousNetResult = totalPreviousNetResult;
        this.totalNetResultVariation = totalNetResultVariation;
        this.totalCurrentMarginPercentage = totalCurrentMarginPercentage;
        this.totalPreviousMarginPercentage = totalPreviousMarginPercentage;
        this.totalMarginPercentageVariation = totalMarginPercentageVariation;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getCurrentFiscalPeriodId() { return currentFiscalPeriodId; }
    public void setCurrentFiscalPeriodId(Long currentFiscalPeriodId) { this.currentFiscalPeriodId = currentFiscalPeriodId; }

    public Long getPreviousFiscalPeriodId() { return previousFiscalPeriodId; }
    public void setPreviousFiscalPeriodId(Long previousFiscalPeriodId) { this.previousFiscalPeriodId = previousFiscalPeriodId; }

    public String getCurrentFiscalPeriodName() { return currentFiscalPeriodName; }
    public void setCurrentFiscalPeriodName(String currentFiscalPeriodName) { this.currentFiscalPeriodName = currentFiscalPeriodName; }

    public String getPreviousFiscalPeriodName() { return previousFiscalPeriodName; }
    public void setPreviousFiscalPeriodName(String previousFiscalPeriodName) { this.previousFiscalPeriodName = previousFiscalPeriodName; }

    public AnalyticalDimensionType getDimensionType() { return dimensionType; }
    public void setDimensionType(AnalyticalDimensionType dimensionType) { this.dimensionType = dimensionType; }

    public List<FinancialComparisonLine> getLines() { return lines; }
    public void setLines(List<FinancialComparisonLine> lines) { this.lines = lines; }

    public BigDecimal getTotalCurrentRevenue() { return totalCurrentRevenue; }
    public void setTotalCurrentRevenue(BigDecimal totalCurrentRevenue) { this.totalCurrentRevenue = totalCurrentRevenue; }

    public BigDecimal getTotalPreviousRevenue() { return totalPreviousRevenue; }
    public void setTotalPreviousRevenue(BigDecimal totalPreviousRevenue) { this.totalPreviousRevenue = totalPreviousRevenue; }

    public BigDecimal getTotalRevenueVariation() { return totalRevenueVariation; }
    public void setTotalRevenueVariation(BigDecimal totalRevenueVariation) { this.totalRevenueVariation = totalRevenueVariation; }

    public BigDecimal getTotalCurrentExpense() { return totalCurrentExpense; }
    public void setTotalCurrentExpense(BigDecimal totalCurrentExpense) { this.totalCurrentExpense = totalCurrentExpense; }

    public BigDecimal getTotalPreviousExpense() { return totalPreviousExpense; }
    public void setTotalPreviousExpense(BigDecimal totalPreviousExpense) { this.totalPreviousExpense = totalPreviousExpense; }

    public BigDecimal getTotalExpenseVariation() { return totalExpenseVariation; }
    public void setTotalExpenseVariation(BigDecimal totalExpenseVariation) { this.totalExpenseVariation = totalExpenseVariation; }

    public BigDecimal getTotalCurrentNetResult() { return totalCurrentNetResult; }
    public void setTotalCurrentNetResult(BigDecimal totalCurrentNetResult) { this.totalCurrentNetResult = totalCurrentNetResult; }

    public BigDecimal getTotalPreviousNetResult() { return totalPreviousNetResult; }
    public void setTotalPreviousNetResult(BigDecimal totalPreviousNetResult) { this.totalPreviousNetResult = totalPreviousNetResult; }

    public BigDecimal getTotalNetResultVariation() { return totalNetResultVariation; }
    public void setTotalNetResultVariation(BigDecimal totalNetResultVariation) { this.totalNetResultVariation = totalNetResultVariation; }

    public BigDecimal getTotalCurrentMarginPercentage() { return totalCurrentMarginPercentage; }
    public void setTotalCurrentMarginPercentage(BigDecimal totalCurrentMarginPercentage) { this.totalCurrentMarginPercentage = totalCurrentMarginPercentage; }

    public BigDecimal getTotalPreviousMarginPercentage() { return totalPreviousMarginPercentage; }
    public void setTotalPreviousMarginPercentage(BigDecimal totalPreviousMarginPercentage) { this.totalPreviousMarginPercentage = totalPreviousMarginPercentage; }

    public BigDecimal getTotalMarginPercentageVariation() { return totalMarginPercentageVariation; }
    public void setTotalMarginPercentageVariation(BigDecimal totalMarginPercentageVariation) { this.totalMarginPercentageVariation = totalMarginPercentageVariation; }
}
