package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class OpeningBalanceResult {

    private Long chartOfAccountsId;
    private Integer sourceFiscalYear;
    private Integer targetFiscalYear;
    private Long openingJournalEntryId;
    private List<OpeningBalanceLine> lines;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private Boolean balanced;
    private Boolean successful;

    public OpeningBalanceResult() {
    }

    public OpeningBalanceResult(Long chartOfAccountsId, Integer sourceFiscalYear, Integer targetFiscalYear, Long openingJournalEntryId, List<OpeningBalanceLine> lines, BigDecimal totalDebit, BigDecimal totalCredit, Boolean balanced, Boolean successful) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.sourceFiscalYear = sourceFiscalYear;
        this.targetFiscalYear = targetFiscalYear;
        this.openingJournalEntryId = openingJournalEntryId;
        this.lines = lines;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.balanced = balanced;
        this.successful = successful;
    }

    public Long getChartOfAccountsId() {
        return chartOfAccountsId;
    }

    public void setChartOfAccountsId(Long chartOfAccountsId) {
        this.chartOfAccountsId = chartOfAccountsId;
    }

    public Integer getSourceFiscalYear() {
        return sourceFiscalYear;
    }

    public void setSourceFiscalYear(Integer sourceFiscalYear) {
        this.sourceFiscalYear = sourceFiscalYear;
    }

    public Integer getTargetFiscalYear() {
        return targetFiscalYear;
    }

    public void setTargetFiscalYear(Integer targetFiscalYear) {
        this.targetFiscalYear = targetFiscalYear;
    }

    public Long getOpeningJournalEntryId() {
        return openingJournalEntryId;
    }

    public void setOpeningJournalEntryId(Long openingJournalEntryId) {
        this.openingJournalEntryId = openingJournalEntryId;
    }

    public List<OpeningBalanceLine> getLines() {
        return lines;
    }

    public void setLines(List<OpeningBalanceLine> lines) {
        this.lines = lines;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Boolean getBalanced() {
        return balanced;
    }

    public void setBalanced(Boolean balanced) {
        this.balanced = balanced;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}