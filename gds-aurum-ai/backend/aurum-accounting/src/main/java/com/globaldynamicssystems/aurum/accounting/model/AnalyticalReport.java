package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class AnalyticalReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private AnalyticalDimensionType dimensionType;
    private List<AnalyticalReportLine> lines;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal totalBalance;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal totalNetResult;

    public AnalyticalReport() {
    }

    public AnalyticalReport(Long chartOfAccountsId, Long fiscalPeriodId, String fiscalPeriodName,
                            AnalyticalDimensionType dimensionType, List<AnalyticalReportLine> lines,
                            BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalBalance,
                            BigDecimal totalRevenue, BigDecimal totalExpense, BigDecimal totalNetResult) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.dimensionType = dimensionType;
        this.lines = lines;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.totalBalance = totalBalance;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.totalNetResult = totalNetResult;
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

    public List<AnalyticalReportLine> getLines() {
        return lines;
    }

    public void setLines(List<AnalyticalReportLine> lines) {
        this.lines = lines;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
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
}
