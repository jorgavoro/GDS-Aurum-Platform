package com.globaldynamicssystems.aurum.accounting.model;

import java.time.LocalDate;

public class AccountingPeriodOperation {

    private Long fiscalPeriodId;
    private LocalDate accountingDate;
    private AccountingOperationType operationType;

    public AccountingPeriodOperation() {
    }

    public AccountingPeriodOperation(Long fiscalPeriodId, LocalDate accountingDate, AccountingOperationType operationType) {
        this.fiscalPeriodId = fiscalPeriodId;
        this.accountingDate = accountingDate;
        this.operationType = operationType;
    }

    public Long getFiscalPeriodId() {
        return fiscalPeriodId;
    }

    public void setFiscalPeriodId(Long fiscalPeriodId) {
        this.fiscalPeriodId = fiscalPeriodId;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public AccountingOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(AccountingOperationType operationType) {
        this.operationType = operationType;
    }
}