package com.globaldynamicssystems.aurum.accounting.model;

public class ClosingEntryMetadata {

    private ClosingEntryType type;
    private Integer fiscalYear;
    private Long sourceChartOfAccountsId;

    public ClosingEntryMetadata() {
    }

    public ClosingEntryMetadata(ClosingEntryType type, Integer fiscalYear, Long sourceChartOfAccountsId) {
        this.type = type;
        this.fiscalYear = fiscalYear;
        this.sourceChartOfAccountsId = sourceChartOfAccountsId;
    }

    public ClosingEntryType getType() {
        return type;
    }

    public void setType(ClosingEntryType type) {
        this.type = type;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Long getSourceChartOfAccountsId() {
        return sourceChartOfAccountsId;
    }

    public void setSourceChartOfAccountsId(Long sourceChartOfAccountsId) {
        this.sourceChartOfAccountsId = sourceChartOfAccountsId;
    }
}