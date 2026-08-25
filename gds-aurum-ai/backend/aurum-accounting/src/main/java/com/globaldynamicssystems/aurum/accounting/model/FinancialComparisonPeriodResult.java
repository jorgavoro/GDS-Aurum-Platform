package com.globaldynamicssystems.aurum.accounting.model;

public class FinancialComparisonPeriodResult {

    private FinancialComparisonPeriod currentPeriod;
    private FinancialComparisonPeriod previousPeriod;
    private FinancialComparisonReport comparisonReport;

    public FinancialComparisonPeriodResult() {
    }

    public FinancialComparisonPeriodResult(FinancialComparisonPeriod currentPeriod,
                                           FinancialComparisonPeriod previousPeriod,
                                           FinancialComparisonReport comparisonReport) {
        this.currentPeriod = currentPeriod;
        this.previousPeriod = previousPeriod;
        this.comparisonReport = comparisonReport;
    }

    public FinancialComparisonPeriod getCurrentPeriod() { return currentPeriod; }
    public void setCurrentPeriod(FinancialComparisonPeriod currentPeriod) { this.currentPeriod = currentPeriod; }

    public FinancialComparisonPeriod getPreviousPeriod() { return previousPeriod; }
    public void setPreviousPeriod(FinancialComparisonPeriod previousPeriod) { this.previousPeriod = previousPeriod; }

    public FinancialComparisonReport getComparisonReport() { return comparisonReport; }
    public void setComparisonReport(FinancialComparisonReport comparisonReport) { this.comparisonReport = comparisonReport; }
}
