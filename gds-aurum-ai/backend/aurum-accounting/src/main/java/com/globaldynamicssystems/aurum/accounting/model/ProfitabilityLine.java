package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class ProfitabilityLine {

    private AnalyticalDimensionType dimensionType;
    private Long dimensionId;
    private String dimensionCode;
    private String dimensionName;
    private BigDecimal revenue;
    private BigDecimal expense;
    private BigDecimal netResult;
    private BigDecimal margin;
    private BigDecimal marginPercentage;

    public ProfitabilityLine() {
    }

    public ProfitabilityLine(AnalyticalDimensionType dimensionType, Long dimensionId,
                             String dimensionCode, String dimensionName,
                             BigDecimal revenue, BigDecimal expense, BigDecimal netResult,
                             BigDecimal margin, BigDecimal marginPercentage) {
        this.dimensionType = dimensionType;
        this.dimensionId = dimensionId;
        this.dimensionCode = dimensionCode;
        this.dimensionName = dimensionName;
        this.revenue = revenue;
        this.expense = expense;
        this.netResult = netResult;
        this.margin = margin;
        this.marginPercentage = marginPercentage;
    }

    public AnalyticalDimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(AnalyticalDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionCode() {
        return dimensionCode;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getNetResult() {
        return netResult;
    }

    public void setNetResult(BigDecimal netResult) {
        this.netResult = netResult;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getMarginPercentage() {
        return marginPercentage;
    }

    public void setMarginPercentage(BigDecimal marginPercentage) {
        this.marginPercentage = marginPercentage;
    }
}
