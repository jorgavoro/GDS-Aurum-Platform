package com.globaldynamicssystems.aurum.accounting.model;

import java.time.LocalDate;

public class FinancialComparisonPeriod {

    private FinancialComparisonPeriodType type;
    private Integer fiscalYear;
    private Integer periodNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String label;

    public FinancialComparisonPeriod() {
    }

    public FinancialComparisonPeriod(FinancialComparisonPeriodType type,
                                     Integer fiscalYear,
                                     Integer periodNumber,
                                     LocalDate startDate,
                                     LocalDate endDate,
                                     String label) {
        this.type = type;
        this.fiscalYear = fiscalYear;
        this.periodNumber = periodNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.label = label;
    }

    public FinancialComparisonPeriodType getType() { return type; }
    public void setType(FinancialComparisonPeriodType type) { this.type = type; }

    public Integer getFiscalYear() { return fiscalYear; }
    public void setFiscalYear(Integer fiscalYear) { this.fiscalYear = fiscalYear; }

    public Integer getPeriodNumber() { return periodNumber; }
    public void setPeriodNumber(Integer periodNumber) { this.periodNumber = periodNumber; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
