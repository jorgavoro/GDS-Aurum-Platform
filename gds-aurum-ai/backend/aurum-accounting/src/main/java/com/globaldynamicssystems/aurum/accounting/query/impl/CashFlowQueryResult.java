package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;

public class CashFlowQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private CashFlowReport cashFlow;

    public CashFlowQueryResult() {
    }

    public CashFlowQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, CashFlowReport cashFlow) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.cashFlow = cashFlow;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.CASH_FLOW;
    }

    @Override
    public Boolean isValid() {
        return cashFlow != null;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public CashFlowReport getCashFlow() { return cashFlow; }
    public void setCashFlow(CashFlowReport cashFlow) { this.cashFlow = cashFlow; }
}