package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import java.util.List;

public class RatioQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<FinancialRatio> ratios;

    public RatioQueryResult() {
    }

    public RatioQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, List<FinancialRatio> ratios) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.ratios = ratios;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.RATIO;
    }

    @Override
    public Boolean isValid() {
        return ratios != null && !ratios.isEmpty();
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<FinancialRatio> getRatios() { return ratios; }
    public void setRatios(List<FinancialRatio> ratios) { this.ratios = ratios; }
}