package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;

public class ProfitabilityQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private ProfitabilityReport profitability;

    public ProfitabilityQueryResult() {
    }

    public ProfitabilityQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, ProfitabilityReport profitability) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.profitability = profitability;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.PROFITABILITY;
    }

    @Override
    public Boolean isValid() {
        return profitability != null;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public ProfitabilityReport getProfitability() { return profitability; }
    public void setProfitability(ProfitabilityReport profitability) { this.profitability = profitability; }
}