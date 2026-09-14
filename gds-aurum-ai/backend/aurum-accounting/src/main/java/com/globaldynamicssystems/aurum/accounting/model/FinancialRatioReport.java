package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class FinancialRatioReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private List<FinancialRatio> ratios;

    public FinancialRatioReport() {
    }

    public FinancialRatioReport(Long chartOfAccountsId,
                                Long fiscalPeriodId,
                                String fiscalPeriodName,
                                List<FinancialRatio> ratios) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.ratios = ratios;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public String getFiscalPeriodName() { return fiscalPeriodName; }
    public void setFiscalPeriodName(String fiscalPeriodName) { this.fiscalPeriodName = fiscalPeriodName; }

    public List<FinancialRatio> getRatios() { return ratios; }
    public void setRatios(List<FinancialRatio> ratios) { this.ratios = ratios; }
}
