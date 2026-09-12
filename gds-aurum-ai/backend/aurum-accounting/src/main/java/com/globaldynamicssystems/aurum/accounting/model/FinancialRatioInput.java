package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class FinancialRatioInput {

    private BigDecimal currentAssets;
    private BigDecimal inventory;
    private BigDecimal cashAndCashEquivalents;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal totalEquity;
    private BigDecimal revenue;
    private BigDecimal costOfRevenue;
    private BigDecimal netIncome;

    public FinancialRatioInput() {
    }

    public FinancialRatioInput(BigDecimal currentAssets,
                               BigDecimal inventory,
                               BigDecimal cashAndCashEquivalents,
                               BigDecimal totalAssets,
                               BigDecimal totalLiabilities,
                               BigDecimal totalEquity,
                               BigDecimal revenue,
                               BigDecimal costOfRevenue,
                               BigDecimal netIncome) {
        this.currentAssets = currentAssets;
        this.inventory = inventory;
        this.cashAndCashEquivalents = cashAndCashEquivalents;
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalEquity = totalEquity;
        this.revenue = revenue;
        this.costOfRevenue = costOfRevenue;
        this.netIncome = netIncome;
    }

    public BigDecimal getCurrentAssets() { return currentAssets; }
    public void setCurrentAssets(BigDecimal currentAssets) { this.currentAssets = currentAssets; }

    public BigDecimal getInventory() { return inventory; }
    public void setInventory(BigDecimal inventory) { this.inventory = inventory; }

    public BigDecimal getCashAndCashEquivalents() { return cashAndCashEquivalents; }
    public void setCashAndCashEquivalents(BigDecimal cashAndCashEquivalents) { this.cashAndCashEquivalents = cashAndCashEquivalents; }

    public BigDecimal getTotalAssets() { return totalAssets; }
    public void setTotalAssets(BigDecimal totalAssets) { this.totalAssets = totalAssets; }

    public BigDecimal getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(BigDecimal totalLiabilities) { this.totalLiabilities = totalLiabilities; }

    public BigDecimal getTotalEquity() { return totalEquity; }
    public void setTotalEquity(BigDecimal totalEquity) { this.totalEquity = totalEquity; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    public BigDecimal getCostOfRevenue() { return costOfRevenue; }
    public void setCostOfRevenue(BigDecimal costOfRevenue) { this.costOfRevenue = costOfRevenue; }

    public BigDecimal getNetIncome() { return netIncome; }
    public void setNetIncome(BigDecimal netIncome) { this.netIncome = netIncome; }
}
