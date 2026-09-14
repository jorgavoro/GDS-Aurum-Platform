package com.globaldynamicssystems.aurum.accounting.model;

import java.util.ArrayList;
import java.util.List;

public class FinancialKpiFilter {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<FinancialKpiType> kpiTypes;

    public FinancialKpiFilter() {
        this.kpiTypes = new ArrayList<>();
    }

    public FinancialKpiFilter(Long chartOfAccountsId, Long fiscalPeriodId) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.kpiTypes = new ArrayList<>();
    }

    public FinancialKpiFilter(Long chartOfAccountsId, Long fiscalPeriodId, List<FinancialKpiType> kpiTypes) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.kpiTypes = (kpiTypes != null) ? kpiTypes : new ArrayList<>();
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

    public List<FinancialKpiType> getKpiTypes() {
        return kpiTypes;
    }

    public void setKpiTypes(List<FinancialKpiType> kpiTypes) {
        this.kpiTypes = kpiTypes;
    }
}