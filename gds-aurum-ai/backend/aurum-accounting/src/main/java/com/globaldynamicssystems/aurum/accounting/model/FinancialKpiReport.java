package com.globaldynamicssystems.aurum.accounting.model;

import java.util.ArrayList;
import java.util.List;

public class FinancialKpiReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private List<FinancialKpi> kpis;
    private Boolean financiallyValid;

    public FinancialKpiReport() {
        this.kpis = new ArrayList<>();
    }

    public FinancialKpiReport(Long chartOfAccountsId, Long fiscalPeriodId, String fiscalPeriodName,
                              List<FinancialKpi> kpis, Boolean financiallyValid) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.kpis = (kpis != null) ? kpis : new ArrayList<>();
        this.financiallyValid = financiallyValid;
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

    public List<FinancialKpi> getKpis() {
        return kpis;
    }

    public void setKpis(List<FinancialKpi> kpis) {
        this.kpis = kpis;
    }

    public Boolean getFinanciallyValid() {
        return financiallyValid;
    }

    public void setFinanciallyValid(Boolean financiallyValid) {
        this.financiallyValid = financiallyValid;
    }
}