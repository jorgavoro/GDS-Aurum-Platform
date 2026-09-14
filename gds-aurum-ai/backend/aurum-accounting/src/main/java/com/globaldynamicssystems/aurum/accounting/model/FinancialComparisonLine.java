package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class FinancialComparisonLine {

    private AnalyticalDimensionType dimensionType;
    private Long dimensionId;
    private String dimensionCode;
    private String dimensionName;

    private BigDecimal currentRevenue;
    private BigDecimal previousRevenue;
    private BigDecimal revenueVariation;
    private BigDecimal revenueVariationPercentage;

    private BigDecimal currentExpense;
    private BigDecimal previousExpense;
    private BigDecimal expenseVariation;
    private BigDecimal expenseVariationPercentage;

    private BigDecimal currentNetResult;
    private BigDecimal previousNetResult;
    private BigDecimal netResultVariation;
    private BigDecimal netResultVariationPercentage;

    private BigDecimal currentMarginPercentage;
    private BigDecimal previousMarginPercentage;
    private BigDecimal marginPercentageVariation;

    public FinancialComparisonLine() {
    }

    public FinancialComparisonLine(AnalyticalDimensionType dimensionType, Long dimensionId,
                                   String dimensionCode, String dimensionName,
                                   BigDecimal currentRevenue, BigDecimal previousRevenue,
                                   BigDecimal revenueVariation, BigDecimal revenueVariationPercentage,
                                   BigDecimal currentExpense, BigDecimal previousExpense,
                                   BigDecimal expenseVariation, BigDecimal expenseVariationPercentage,
                                   BigDecimal currentNetResult, BigDecimal previousNetResult,
                                   BigDecimal netResultVariation, BigDecimal netResultVariationPercentage,
                                   BigDecimal currentMarginPercentage, BigDecimal previousMarginPercentage,
                                   BigDecimal marginPercentageVariation) {
        this.dimensionType = dimensionType;
        this.dimensionId = dimensionId;
        this.dimensionCode = dimensionCode;
        this.dimensionName = dimensionName;
        this.currentRevenue = currentRevenue;
        this.previousRevenue = previousRevenue;
        this.revenueVariation = revenueVariation;
        this.revenueVariationPercentage = revenueVariationPercentage;
        this.currentExpense = currentExpense;
        this.previousExpense = previousExpense;
        this.expenseVariation = expenseVariation;
        this.expenseVariationPercentage = expenseVariationPercentage;
        this.currentNetResult = currentNetResult;
        this.previousNetResult = previousNetResult;
        this.netResultVariation = netResultVariation;
        this.netResultVariationPercentage = netResultVariationPercentage;
        this.currentMarginPercentage = currentMarginPercentage;
        this.previousMarginPercentage = previousMarginPercentage;
        this.marginPercentageVariation = marginPercentageVariation;
    }

    public AnalyticalDimensionType getDimensionType() { return dimensionType; }
    public void setDimensionType(AnalyticalDimensionType dimensionType) { this.dimensionType = dimensionType; }

    public Long getDimensionId() { return dimensionId; }
    public void setDimensionId(Long dimensionId) { this.dimensionId = dimensionId; }

    public String getDimensionCode() { return dimensionCode; }
    public void setDimensionCode(String dimensionCode) { this.dimensionCode = dimensionCode; }

    public String getDimensionName() { return dimensionName; }
    public void setDimensionName(String dimensionName) { this.dimensionName = dimensionName; }

    public BigDecimal getCurrentRevenue() { return currentRevenue; }
    public void setCurrentRevenue(BigDecimal currentRevenue) { this.currentRevenue = currentRevenue; }

    public BigDecimal getPreviousRevenue() { return previousRevenue; }
    public void setPreviousRevenue(BigDecimal previousRevenue) { this.previousRevenue = previousRevenue; }

    public BigDecimal getRevenueVariation() { return revenueVariation; }
    public void setRevenueVariation(BigDecimal revenueVariation) { this.revenueVariation = revenueVariation; }

    public BigDecimal getRevenueVariationPercentage() { return revenueVariationPercentage; }
    public void setRevenueVariationPercentage(BigDecimal revenueVariationPercentage) { this.revenueVariationPercentage = revenueVariationPercentage; }

    public BigDecimal getCurrentExpense() { return currentExpense; }
    public void setCurrentExpense(BigDecimal currentExpense) { this.currentExpense = currentExpense; }

    public BigDecimal getPreviousExpense() { return previousExpense; }
    public void setPreviousExpense(BigDecimal previousExpense) { this.previousExpense = previousExpense; }

    public BigDecimal getExpenseVariation() { return expenseVariation; }
    public void setExpenseVariation(BigDecimal expenseVariation) { this.expenseVariation = expenseVariation; }

    public BigDecimal getExpenseVariationPercentage() { return expenseVariationPercentage; }
    public void setExpenseVariationPercentage(BigDecimal expenseVariationPercentage) { this.expenseVariationPercentage = expenseVariationPercentage; }

    public BigDecimal getCurrentNetResult() { return currentNetResult; }
    public void setCurrentNetResult(BigDecimal currentNetResult) { this.currentNetResult = currentNetResult; }

    public BigDecimal getPreviousNetResult() { return previousNetResult; }
    public void setPreviousNetResult(BigDecimal previousNetResult) { this.previousNetResult = previousNetResult; }

    public BigDecimal getNetResultVariation() { return netResultVariation; }
    public void setNetResultVariation(BigDecimal netResultVariation) { this.netResultVariation = netResultVariation; }

    public BigDecimal getNetResultVariationPercentage() { return netResultVariationPercentage; }
    public void setNetResultVariationPercentage(BigDecimal netResultVariationPercentage) { this.netResultVariationPercentage = netResultVariationPercentage; }

    public BigDecimal getCurrentMarginPercentage() { return currentMarginPercentage; }
    public void setCurrentMarginPercentage(BigDecimal currentMarginPercentage) { this.currentMarginPercentage = currentMarginPercentage; }

    public BigDecimal getPreviousMarginPercentage() { return previousMarginPercentage; }
    public void setPreviousMarginPercentage(BigDecimal previousMarginPercentage) { this.previousMarginPercentage = previousMarginPercentage; }

    public BigDecimal getMarginPercentageVariation() { return marginPercentageVariation; }
    public void setMarginPercentageVariation(BigDecimal marginPercentageVariation) { this.marginPercentageVariation = marginPercentageVariation; }
}
