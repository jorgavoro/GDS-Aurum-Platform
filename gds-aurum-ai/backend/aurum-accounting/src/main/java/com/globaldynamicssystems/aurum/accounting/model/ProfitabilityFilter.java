package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class ProfitabilityFilter {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private AnalyticalDimensionType dimensionType;
    private List<Long> dimensionIds;
    private Boolean includeInactive;

    public ProfitabilityFilter() {
        this.includeInactive = Boolean.FALSE;
    }

    public ProfitabilityFilter(Long chartOfAccountsId, Long fiscalPeriodId,
                               AnalyticalDimensionType dimensionType, List<Long> dimensionIds,
                               Boolean includeInactive) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.dimensionType = dimensionType;
        this.dimensionIds = dimensionIds;
        this.includeInactive = includeInactive != null ? includeInactive : Boolean.FALSE;
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

    public AnalyticalDimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(AnalyticalDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public List<Long> getDimensionIds() {
        return dimensionIds;
    }

    public void setDimensionIds(List<Long> dimensionIds) {
        this.dimensionIds = dimensionIds;
    }

    public Boolean getIncludeInactive() {
        return includeInactive;
    }

    public void setIncludeInactive(Boolean includeInactive) {
        this.includeInactive = includeInactive != null ? includeInactive : Boolean.FALSE;
    }
}
