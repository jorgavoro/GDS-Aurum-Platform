package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class FinancialRatioFilter {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<FinancialRatioType> ratioTypes;

    public FinancialRatioFilter() {
    }

    public FinancialRatioFilter(Long chartOfAccountsId,
                                Long fiscalPeriodId,
                                List<FinancialRatioType> ratioTypes) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.ratioTypes = ratioTypes;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<FinancialRatioType> getRatioTypes() { return ratioTypes; }
    public void setRatioTypes(List<FinancialRatioType> ratioTypes) { this.ratioTypes = ratioTypes; }
}
