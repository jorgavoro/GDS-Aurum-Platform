package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class CashFlowLine {

    private Long accountId;
    private String accountCode;
    private String accountName;
    private CashFlowActivityType activityType;
    private BigDecimal cashInflow;
    private BigDecimal cashOutflow;
    private BigDecimal netCashFlow;

    public CashFlowLine() {
    }

    public CashFlowLine(Long accountId,
                        String accountCode,
                        String accountName,
                        CashFlowActivityType activityType,
                        BigDecimal cashInflow,
                        BigDecimal cashOutflow,
                        BigDecimal netCashFlow) {
        this.accountId = accountId;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.activityType = activityType;
        this.cashInflow = cashInflow;
        this.cashOutflow = cashOutflow;
        this.netCashFlow = netCashFlow;
    }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public CashFlowActivityType getActivityType() { return activityType; }
    public void setActivityType(CashFlowActivityType activityType) { this.activityType = activityType; }

    public BigDecimal getCashInflow() { return cashInflow; }
    public void setCashInflow(BigDecimal cashInflow) { this.cashInflow = cashInflow; }

    public BigDecimal getCashOutflow() { return cashOutflow; }
    public void setCashOutflow(BigDecimal cashOutflow) { this.cashOutflow = cashOutflow; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }
}
