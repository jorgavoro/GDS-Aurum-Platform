package com.globaldynamicssystems.aurum.accounting.model;

public class FiscalPeriodClosingResult {

    private Long fiscalPeriodId;
    private boolean successful;
    private boolean balanced;
    private boolean hasDraftEntries;
    private boolean hasValidatedEntries;
    private boolean hasPostingInconsistencies;
    private String message;

    public FiscalPeriodClosingResult() {
    }

    public FiscalPeriodClosingResult(
            Long fiscalPeriodId,
            boolean successful,
            boolean balanced,
            boolean hasDraftEntries,
            boolean hasValidatedEntries,
            boolean hasPostingInconsistencies,
            String message) {
        this.fiscalPeriodId = fiscalPeriodId;
        this.successful = successful;
        this.balanced = balanced;
        this.hasDraftEntries = hasDraftEntries;
        this.hasValidatedEntries = hasValidatedEntries;
        this.hasPostingInconsistencies = hasPostingInconsistencies;
        this.message = message;
    }

    public Long getFiscalPeriodId() {
        return fiscalPeriodId;
    }

    public void setFiscalPeriodId(Long fiscalPeriodId) {
        this.fiscalPeriodId = fiscalPeriodId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    public boolean isHasDraftEntries() {
        return hasDraftEntries;
    }

    public void setHasDraftEntries(boolean hasDraftEntries) {
        this.hasDraftEntries = hasDraftEntries;
    }

    public boolean isHasValidatedEntries() {
        return hasValidatedEntries;
    }

    public void setHasValidatedEntries(boolean hasValidatedEntries) {
        this.hasValidatedEntries = hasValidatedEntries;
    }

    public boolean isHasPostingInconsistencies() {
        return hasPostingInconsistencies;
    }

    public void setHasPostingInconsistencies(boolean hasPostingInconsistencies) {
        this.hasPostingInconsistencies = hasPostingInconsistencies;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}