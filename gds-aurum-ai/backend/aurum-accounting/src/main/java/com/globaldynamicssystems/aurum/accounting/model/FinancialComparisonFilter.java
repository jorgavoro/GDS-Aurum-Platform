package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class FinancialComparisonFilter {

    private Long chartOfAccountsId;
    private Long currentFiscalPeriodId;
    private Long previousFiscalPeriodId;
    private AnalyticalDimensionType dimensionType;
    private List<Long> dimensionIds;
    private Boolean includeInactive;

    public FinancialComparisonFilter() {
        this.includeInactive = Boolean.FALSE;
    }

    public FinancialComparisonFilter(Long chartOfAccountsId,
                                     Long currentFiscalPeriodId, Long previousFiscalPeriodId,
                                     AnalyticalDimensionType dimensionType, List<Long> dimensionIds,
                                     Boolean includeInactive) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.currentFiscalPeriodId = currentFiscalPeriodId;
        this.previousFiscalPeriodId = previousFiscalPeriodId;
        this.dimensionType = dimensionType;
        this.dimensionIds = dimensionIds;
        this.includeInactive = includeInactive != null ? includeInactive : Boolean.FALSE;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getCurrentFiscalPeriodId() { return currentFiscalPeriodId; }
    public void setCurrentFiscalPeriodId(Long currentFiscalPeriodId) { this.currentFiscalPeriodId = currentFiscalPeriodId; }

    public Long getPreviousFiscalPeriodId() { return previousFiscalPeriodId; }
    public void setPreviousFiscalPeriodId(Long previousFiscalPeriodId) { this.previousFiscalPeriodId = previousFiscalPeriodId; }

    public AnalyticalDimensionType getDimensionType() { return dimensionType; }
    public void setDimensionType(AnalyticalDimensionType dimensionType) { this.dimensionType = dimensionType; }

    public List<Long> getDimensionIds() { return dimensionIds; }
    public void setDimensionIds(List<Long> dimensionIds) { this.dimensionIds = dimensionIds; }

    public Boolean getIncludeInactive() { return includeInactive; }
    public void setIncludeInactive(Boolean includeInactive) { this.includeInactive = includeInactive != null ? includeInactive : Boolean.FALSE; }
}
