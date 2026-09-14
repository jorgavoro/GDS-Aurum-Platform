package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class AccountFinancialAnalysis {

    private Long accountId;
    private String accountCode;
    private String accountName;
    private AccountType accountType;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
    private BigDecimal revenue;
    private BigDecimal expense;
    private BigDecimal netResult;

    public AccountFinancialAnalysis() {
    }

    public AccountFinancialAnalysis(Long accountId,
                                    String accountCode,
                                    String accountName,
                                    AccountType accountType,
                                    Long fiscalPeriodId,
                                    String fiscalPeriodName,
                                    BigDecimal debit,
                                    BigDecimal credit,
                                    BigDecimal balance,
                                    BigDecimal revenue,
                                    BigDecimal expense,
                                    BigDecimal netResult) {
        this.accountId = accountId;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.accountType = accountType;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.revenue = revenue;
        this.expense = expense;
        this.netResult = netResult;
    }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public String getFiscalPeriodName() { return fiscalPeriodName; }
    public void setFiscalPeriodName(String fiscalPeriodName) { this.fiscalPeriodName = fiscalPeriodName; }

    public BigDecimal getDebit() { return debit; }
    public void setDebit(BigDecimal debit) { this.debit = debit; }

    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    public BigDecimal getExpense() { return expense; }
    public void setExpense(BigDecimal expense) { this.expense = expense; }

    public BigDecimal getNetResult() { return netResult; }
    public void setNetResult(BigDecimal netResult) { this.netResult = netResult; }
}
