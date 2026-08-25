package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class FinancialPeriodComparisonRequest {

    private Long chartOfAccountsId;
    private FinancialComparisonPeriodType periodType;
    private Integer currentFiscalYear;
    private Integer currentPeriodNumber;
    private AnalyticalDimensionType dimensionType;
    private List<Long> dimensionIds;
    private Boolean includeInactive;

    public FinancialPeriodComparisonRequest() {
    }

    public FinancialPeriodComparisonRequest(Long chartOfAccountsId,
                                            FinancialComparisonPeriodType periodType,
                                            Integer currentFiscalYear,
                                            Integer currentPeriodNumber,
                                            AnalyticalDimensionType dimensionType,
                                            List<Long> dimensionIds,
                                            Boolean includeInactive) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.periodType = periodType;
        this.currentFiscalYear = currentFiscalYear;
        this.currentPeriodNumber = currentPeriodNumber;
        this.dimensionType = dimensionType;
        this.dimensionIds = dimensionIds;
        this.includeInactive = includeInactive;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public FinancialComparisonPeriodType getPeriodType() { return periodType; }
    public void setPeriodType(FinancialComparisonPeriodType periodType) { this.periodType = periodType; }

    public Integer getCurrentFiscalYear() { return currentFiscalYear; }
    public void setCurrentFiscalYear(Integer currentFiscalYear) { this.currentFiscalYear = currentFiscalYear; }

    public Integer getCurrentPeriodNumber() { return currentPeriodNumber; }
    public void setCurrentPeriodNumber(Integer currentPeriodNumber) { this.currentPeriodNumber = currentPeriodNumber; }

    public AnalyticalDimensionType getDimensionType() { return dimensionType; }
    public void setDimensionType(AnalyticalDimensionType dimensionType) { this.dimensionType = dimensionType; }

    public List<Long> getDimensionIds() { return dimensionIds; }
    public void setDimensionIds(List<Long> dimensionIds) { this.dimensionIds = dimensionIds; }

    public Boolean getIncludeInactive() { return includeInactive; }
    public void setIncludeInactive(Boolean includeInactive) { this.includeInactive = includeInactive; }
}
