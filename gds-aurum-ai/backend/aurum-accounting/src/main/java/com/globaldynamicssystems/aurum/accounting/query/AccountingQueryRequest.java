package com.globaldynamicssystems.aurum.accounting.query;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiType;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioType;

import java.util.ArrayList;
import java.util.List;

public class AccountingQueryRequest {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private AccountingQueryType queryType;
    private List<Long> accountIds;
    private List<FinancialKpiType> kpiTypes;
    private List<FinancialRatioType> ratioTypes;
    private AnalyticalDimensionType dimensionType;
    private List<Long> dimensionIds;
    private Long currentFiscalPeriodId;
    private Long previousFiscalPeriodId;

    public AccountingQueryRequest() {
        this.accountIds = new ArrayList<>();
        this.kpiTypes = new ArrayList<>();
        this.ratioTypes = new ArrayList<>();
        this.dimensionIds = new ArrayList<>();
    }

    public AccountingQueryRequest(Long chartOfAccountsId, Long fiscalPeriodId, AccountingQueryType queryType,
                                  List<Long> accountIds, List<FinancialKpiType> kpiTypes,
                                  List<FinancialRatioType> ratioTypes, AnalyticalDimensionType dimensionType,
                                  List<Long> dimensionIds, Long currentFiscalPeriodId, Long previousFiscalPeriodId) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.queryType = queryType;
        this.accountIds = accountIds != null ? accountIds : new ArrayList<>();
        this.kpiTypes = kpiTypes != null ? kpiTypes : new ArrayList<>();
        this.ratioTypes = ratioTypes != null ? ratioTypes : new ArrayList<>();
        this.dimensionType = dimensionType;
        this.dimensionIds = dimensionIds != null ? dimensionIds : new ArrayList<>();
        this.currentFiscalPeriodId = currentFiscalPeriodId;
        this.previousFiscalPeriodId = previousFiscalPeriodId;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public AccountingQueryType getQueryType() { return queryType; }
    public void setQueryType(AccountingQueryType queryType) { this.queryType = queryType; }

    public List<Long> getAccountIds() { return accountIds; }
    public void setAccountIds(List<Long> accountIds) { this.accountIds = accountIds; }

    public List<FinancialKpiType> getKpiTypes() { return kpiTypes; }
    public void setKpiTypes(List<FinancialKpiType> kpiTypes) { this.kpiTypes = kpiTypes; }

    public List<FinancialRatioType> getRatioTypes() { return ratioTypes; }
    public void setRatioTypes(List<FinancialRatioType> ratioTypes) { this.ratioTypes = ratioTypes; }

    public AnalyticalDimensionType getDimensionType() { return dimensionType; }
    public void setDimensionType(AnalyticalDimensionType dimensionType) { this.dimensionType = dimensionType; }

    public List<Long> getDimensionIds() { return dimensionIds; }
    public void setDimensionIds(List<Long> dimensionIds) { this.dimensionIds = dimensionIds; }

    public Long getCurrentFiscalPeriodId() { return currentFiscalPeriodId; }
    public void setCurrentFiscalPeriodId(Long currentFiscalPeriodId) { this.currentFiscalPeriodId = currentFiscalPeriodId; }

    public Long getPreviousFiscalPeriodId() { return previousFiscalPeriodId; }
    public void setPreviousFiscalPeriodId(Long previousFiscalPeriodId) { this.previousFiscalPeriodId = previousFiscalPeriodId; }
}