package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class OpeningBalanceLine {

    private Long accountId;
    private String accountCode;
    private String accountName;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;

    public OpeningBalanceLine() {
    }

    public OpeningBalanceLine(Long accountId, String accountCode, String accountName, BigDecimal debit, BigDecimal credit, BigDecimal balance) {
        this.accountId = accountId;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}