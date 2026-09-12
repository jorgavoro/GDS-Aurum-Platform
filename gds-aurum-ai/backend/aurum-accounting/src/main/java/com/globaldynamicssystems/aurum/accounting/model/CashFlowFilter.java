package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class CashFlowFilter {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<Long> accountIds;
    private List<CashFlowActivityType> activityTypes;
    private Boolean includeInactive;

    public CashFlowFilter() {
    }

    public CashFlowFilter(Long chartOfAccountsId,
                          Long fiscalPeriodId,
                          List<Long> accountIds,
                          List<CashFlowActivityType> activityTypes,
                          Boolean includeInactive) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.accountIds = accountIds;
        this.activityTypes = activityTypes;
        this.includeInactive = includeInactive;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<Long> getAccountIds() { return accountIds; }
    public void setAccountIds(List<Long> accountIds) { this.accountIds = accountIds; }

    public List<CashFlowActivityType> getActivityTypes() { return activityTypes; }
    public void setActivityTypes(List<CashFlowActivityType> activityTypes) { this.activityTypes = activityTypes; }

    public Boolean getIncludeInactive() { return includeInactive; }
    public void setIncludeInactive(Boolean includeInactive) { this.includeInactive = includeInactive; }
}
