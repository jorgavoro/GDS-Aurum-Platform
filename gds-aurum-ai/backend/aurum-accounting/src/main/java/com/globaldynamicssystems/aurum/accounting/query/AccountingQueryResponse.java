package com.globaldynamicssystems.aurum.accounting.query;

public class AccountingQueryResponse {

    private AccountingQueryType queryType;
    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private Object data;
    private Boolean valid;

    public AccountingQueryResponse() {
    }

    public AccountingQueryResponse(AccountingQueryType queryType, Long chartOfAccountsId, Long fiscalPeriodId,
                                   String fiscalPeriodName, Object data, Boolean valid) {
        this.queryType = queryType;
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.data = data;
        this.valid = valid;
    }

    public AccountingQueryType getQueryType() { return queryType; }
    public void setQueryType(AccountingQueryType queryType) { this.queryType = queryType; }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public String getFiscalPeriodName() { return fiscalPeriodName; }
    public void setFiscalPeriodName(String fiscalPeriodName) { this.fiscalPeriodName = fiscalPeriodName; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public Boolean getValid() { return valid; }
    public void setValid(Boolean valid) { this.valid = valid; }
}