package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class BalanceSheet {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String chartOfAccountsCode;
    private String fiscalPeriodName;
    private List<FinancialStatementLine> assets;
    private List<FinancialStatementLine> liabilities;
    private List<FinancialStatementLine> equity;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal totalEquity;
    private BigDecimal liabilitiesAndEquity;
    private Boolean balanced;

    public BalanceSheet() {
    }

    public BalanceSheet(
            Long chartOfAccountsId,
            Long fiscalPeriodId,
            String chartOfAccountsCode,
            String fiscalPeriodName,
            List<FinancialStatementLine> assets,
            List<FinancialStatementLine> liabilities,
            List<FinancialStatementLine> equity,
            BigDecimal totalAssets,
            BigDecimal totalLiabilities,
            BigDecimal totalEquity,
            BigDecimal liabilitiesAndEquity,
            Boolean balanced) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.chartOfAccountsCode = chartOfAccountsCode;
        this.fiscalPeriodName = fiscalPeriodName;
        this.assets = assets;
        this.liabilities = liabilities;
        this.equity = equity;
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalEquity = totalEquity;
        this.liabilitiesAndEquity = liabilitiesAndEquity;
        this.balanced = balanced;
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

    public List<FinancialStatementLine> getAssets() {
        return assets;
    }

    public void setAssets(List<FinancialStatementLine> assets) {
        this.assets = assets;
    }

    public List<FinancialStatementLine> getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(List<FinancialStatementLine> liabilities) {
        this.liabilities = liabilities;
    }

    public List<FinancialStatementLine> getEquity() {
        return equity;
    }

    public void setEquity(List<FinancialStatementLine> equity) {
        this.equity = equity;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(BigDecimal totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public BigDecimal getTotalEquity() {
        return totalEquity;
    }

    public void setTotalEquity(BigDecimal totalEquity) {
        this.totalEquity = totalEquity;
    }

    public BigDecimal getLiabilitiesAndEquity() {
        return liabilitiesAndEquity;
    }

    public void setLiabilitiesAndEquity(BigDecimal liabilitiesAndEquity) {
        this.liabilitiesAndEquity = liabilitiesAndEquity;
    }

    public Boolean getBalanced() {
        return balanced;
    }

    public Boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(Boolean balanced) {
        this.balanced = balanced;
    }
}