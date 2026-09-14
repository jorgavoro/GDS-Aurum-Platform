package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class IncomeStatement {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String chartOfAccountsCode;
    private String fiscalPeriodName;
    private List<FinancialStatementLine> revenues;
    private List<FinancialStatementLine> expenses;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netIncome;

    public IncomeStatement() {
    }

    public IncomeStatement(
            Long chartOfAccountsId,
            Long fiscalPeriodId,
            String chartOfAccountsCode,
            String fiscalPeriodName,
            List<FinancialStatementLine> revenues,
            List<FinancialStatementLine> expenses,
            BigDecimal totalRevenue,
            BigDecimal totalExpense,
            BigDecimal netIncome) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.chartOfAccountsCode = chartOfAccountsCode;
        this.fiscalPeriodName = fiscalPeriodName;
        this.revenues = revenues;
        this.expenses = expenses;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.netIncome = netIncome;
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

    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
    }

    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }

    public String getFiscalPeriodName() {
        return fiscalPeriodName;
    }

    public void setFiscalPeriodName(String fiscalPeriodName) {
        this.fiscalPeriodName = fiscalPeriodName;
    }

    public List<FinancialStatementLine> getRevenues() {
        return revenues;
    }

    public void setRevenues(List<FinancialStatementLine> revenues) {
        this.revenues = revenues;
    }

    public List<FinancialStatementLine> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<FinancialStatementLine> expenses) {
        this.expenses = expenses;
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

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }
}