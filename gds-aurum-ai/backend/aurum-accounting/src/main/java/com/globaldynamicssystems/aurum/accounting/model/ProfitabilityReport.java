package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class ProfitabilityReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private AnalyticalDimensionType dimensionType;
    private List<ProfitabilityLine> lines;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal totalNetResult;
    private BigDecimal totalMargin;
    private BigDecimal totalMarginPercentage;

    public ProfitabilityReport() {
    }

    public ProfitabilityReport(Long chartOfAccountsId, Long fiscalPeriodId, String fiscalPeriodName,
                               AnalyticalDimensionType dimensionType, List<ProfitabilityLine> lines,
                               BigDecimal totalRevenue, BigDecimal totalExpense,
                               BigDecimal totalNetResult, BigDecimal totalMargin,
                               BigDecimal totalMarginPercentage) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.dimensionType = dimensionType;
        this.lines = lines;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.totalNetResult = totalNetResult;
        this.totalMargin = totalMargin;
        this.totalMarginPercentage = totalMarginPercentage;
    }

    public Long getChartOfAccountsId() {
        return chartOfAccountsId;
    }

    public void setChartOfAccountsId(Long chartOfAccountsId) {
        this.chartOfAccountsId = chartOfAccountsId;
    }

    public Long getFiscalPeriodId() {
        return fiscalPeriodId;
    }

    public void setFiscalPeriodId(Long fiscalPeriodId) {
        this.fiscalPeriodId = fiscalPeriodId;
    }

    public String getFiscalPeriodName() {
        return fiscalPeriodName;
    }

    public void setFiscalPeriodName(String fiscalPeriodName) {
        this.fiscalPeriodName = fiscalPeriodName;
    }

    public AnalyticalDimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(AnalyticalDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public List<ProfitabilityLine> getLines() {
        return lines;
    }

    public void setLines(List<ProfitabilityLine> lines) {
        this.lines = lines;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public BigDecimal getTotalNetResult() {
        return totalNetResult;
    }

    public void setTotalNetResult(BigDecimal totalNetResult) {
        this.totalNetResult = totalNetResult;
    }

    public BigDecimal getTotalMargin() {
        return totalMargin;
    }

    public void setTotalMargin(BigDecimal totalMargin) {
        this.totalMargin = totalMargin;
    }

    public BigDecimal getTotalMarginPercentage() {
        return totalMarginPercentage;
    }

    public void setTotalMarginPercentage(BigDecimal totalMarginPercentage) {
        this.totalMarginPercentage = totalMarginPercentage;
    }
}
