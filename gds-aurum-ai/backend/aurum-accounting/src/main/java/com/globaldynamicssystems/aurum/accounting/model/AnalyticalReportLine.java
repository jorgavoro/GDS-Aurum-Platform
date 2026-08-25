package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class AnalyticalReportLine {

    private AnalyticalDimensionType dimensionType;
    private Long dimensionId;
    private String dimensionCode;
    private String dimensionName;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
    private BigDecimal revenue;
    private BigDecimal expense;
    private BigDecimal netResult;

    public AnalyticalReportLine() {
    }

    public AnalyticalReportLine(AnalyticalDimensionType dimensionType, Long dimensionId,
                                String dimensionCode, String dimensionName,
                                BigDecimal debit, BigDecimal credit, BigDecimal balance,
                                BigDecimal revenue, BigDecimal expense, BigDecimal netResult) {
        this.dimensionType = dimensionType;
        this.dimensionId = dimensionId;
        this.dimensionCode = dimensionCode;
        this.dimensionName = dimensionName;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.revenue = revenue;
        this.expense = expense;
        this.netResult = netResult;
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

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
}
