package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class AccountFinancialAnalysisFilter {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<Long> accountIds;
    private List<AccountType> accountTypes;
    private String accountCodeFrom;
    private String accountCodeTo;
    private Boolean includeInactive = false;

    public AccountFinancialAnalysisFilter() {
    }

    public AccountFinancialAnalysisFilter(Long chartOfAccountsId,
                                          Long fiscalPeriodId,
                                          List<Long> accountIds,
                                          List<AccountType> accountTypes,
                                          String accountCodeFrom,
                                          String accountCodeTo,
                                          Boolean includeInactive) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.accountIds = accountIds;
        this.accountTypes = accountTypes;
        this.accountCodeFrom = accountCodeFrom;
        this.accountCodeTo = accountCodeTo;
        this.includeInactive = includeInactive;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<Long> getAccountIds() { return accountIds; }
    public void setAccountIds(List<Long> accountIds) { this.accountIds = accountIds; }

    public List<AccountType> getAccountTypes() { return accountTypes; }
    public void setAccountTypes(List<AccountType> accountTypes) { this.accountTypes = accountTypes; }

    public String getAccountCodeFrom() { return accountCodeFrom; }
    public void setAccountCodeFrom(String accountCodeFrom) { this.accountCodeFrom = accountCodeFrom; }

    public String getAccountCodeTo() { return accountCodeTo; }
    public void setAccountCodeTo(String accountCodeTo) { this.accountCodeTo = accountCodeTo; }

    public Boolean getIncludeInactive() { return includeInactive; }
    public void setIncludeInactive(Boolean includeInactive) { this.includeInactive = includeInactive; }
}
