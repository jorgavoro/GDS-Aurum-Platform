package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;

public class ComparisonQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long currentFiscalPeriodId;
    private Long previousFiscalPeriodId;
    private FinancialComparisonReport comparison;

    public ComparisonQueryResult() {
    }

    public ComparisonQueryResult(Long chartOfAccountsId, Long currentFiscalPeriodId, Long previousFiscalPeriodId,
                                 FinancialComparisonReport comparison) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.currentFiscalPeriodId = currentFiscalPeriodId;
        this.previousFiscalPeriodId = previousFiscalPeriodId;
        this.comparison = comparison;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.COMPARISON;
    }

    @Override
    public Boolean isValid() {
        return comparison != null;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getCurrentFiscalPeriodId() { return currentFiscalPeriodId; }
    public void setCurrentFiscalPeriodId(Long currentFiscalPeriodId) { this.currentFiscalPeriodId = currentFiscalPeriodId; }

    public Long getPreviousFiscalPeriodId() { return previousFiscalPeriodId; }
    public void setPreviousFiscalPeriodId(Long previousFiscalPeriodId) { this.previousFiscalPeriodId = previousFiscalPeriodId; }

    public FinancialComparisonReport getComparison() { return comparison; }
    public void setComparison(FinancialComparisonReport comparison) { this.comparison = comparison; }
}