package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysis;

import java.math.BigDecimal;
import java.util.List;

public class AccountQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<AccountFinancialAnalysis> accounts;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal totalBalance;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal totalNetResult;

    public AccountQueryResult() {
    }

    public AccountQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, List<AccountFinancialAnalysis> accounts,
                              BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalBalance,
                              BigDecimal totalRevenue, BigDecimal totalExpense, BigDecimal totalNetResult) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.accounts = accounts;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.totalBalance = totalBalance;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.totalNetResult = totalNetResult;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.ACCOUNT;
    }

    @Override
    public Boolean isValid() {
        return true;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<AccountFinancialAnalysis> getAccounts() { return accounts; }
    public void setAccounts(List<AccountFinancialAnalysis> accounts) { this.accounts = accounts; }

    public BigDecimal getTotalDebit() { return totalDebit; }
    public void setTotalDebit(BigDecimal totalDebit) { this.totalDebit = totalDebit; }

    public BigDecimal getTotalCredit() { return totalCredit; }
    public void setTotalCredit(BigDecimal totalCredit) { this.totalCredit = totalCredit; }

    public BigDecimal getTotalBalance() { return totalBalance; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }

    public BigDecimal getTotalNetResult() { return totalNetResult; }
    public void setTotalNetResult(BigDecimal totalNetResult) { this.totalNetResult = totalNetResult; }
}