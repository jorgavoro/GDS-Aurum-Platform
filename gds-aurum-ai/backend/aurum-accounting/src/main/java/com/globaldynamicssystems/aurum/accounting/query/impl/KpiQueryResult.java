package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpi;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;

import java.util.List;

public class KpiQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<FinancialKpi> kpis;
    private Boolean financiallyValid;

    public KpiQueryResult() {
    }

    public KpiQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, List<FinancialKpi> kpis, Boolean financiallyValid) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.kpis = kpis;
        this.financiallyValid = financiallyValid;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.KPI;
    }

    @Override
    public Boolean isValid() {
        return financiallyValid != null ? financiallyValid : false;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<FinancialKpi> getKpis() { return kpis; }
    public void setKpis(List<FinancialKpi> kpis) { this.kpis = kpis; }

    public Boolean getFinanciallyValid() { return financiallyValid; }
    public void setFinanciallyValid(Boolean financiallyValid) { this.financiallyValid = financiallyValid; }
}