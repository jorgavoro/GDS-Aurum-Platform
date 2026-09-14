package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class FiscalYearClosingResult {

    private Long chartOfAccountsId;
    private Integer fiscalYear;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netIncome;
    private Long closingJournalEntryId;
    private Boolean successful;

    public FiscalYearClosingResult() {
    }

    public FiscalYearClosingResult(Long chartOfAccountsId,
                                  Integer fiscalYear,
                                  BigDecimal totalRevenue,
                                  BigDecimal totalExpense,
                                  BigDecimal netIncome,
                                  Long closingJournalEntryId,
                                  Boolean successful) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalYear = fiscalYear;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.netIncome = netIncome;
        this.closingJournalEntryId = closingJournalEntryId;
        this.successful = successful;
    }

    public Long getChartOfAccountsId() {
        return chartOfAccountsId;
    }

    public void setChartOfAccountsId(Long chartOfAccountsId) {
        this.chartOfAccountsId = chartOfAccountsId;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public Long getClosingJournalEntryId() {
        return closingJournalEntryId;
    }

    public void setClosingJournalEntryId(Long closingJournalEntryId) {
        this.closingJournalEntryId = closingJournalEntryId;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Boolean isSuccessful() {
        return successful;
    }
}