package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.model.DataQualityFinding;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;

import java.util.List;

public class DataQualityQueryResult implements AccountingQueryResult {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<DataQualityFinding> findings;
    private Integer errorCount;
    private Integer warningCount;
    private Integer infoCount;
    private Boolean valid;

    public DataQualityQueryResult() {
    }

    public DataQualityQueryResult(Long chartOfAccountsId, Long fiscalPeriodId, List<DataQualityFinding> findings,
                                  Integer errorCount, Integer warningCount, Integer infoCount, Boolean valid) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.findings = findings;
        this.errorCount = errorCount;
        this.warningCount = warningCount;
        this.infoCount = infoCount;
        this.valid = valid;
    }

    @Override
    public AccountingQueryType getQueryType() {
        return AccountingQueryType.DATA_QUALITY;
    }

    @Override
    public Boolean isValid() {
        return valid != null ? valid : false;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public List<DataQualityFinding> getFindings() { return findings; }
    public void setFindings(List<DataQualityFinding> findings) { this.findings = findings; }

    public Integer getErrorCount() { return errorCount; }
    public void setErrorCount(Integer errorCount) { this.errorCount = errorCount; }

    public Integer getWarningCount() { return warningCount; }
    public void setWarningCount(Integer warningCount) { this.warningCount = warningCount; }

    public Integer getInfoCount() { return infoCount; }
    public void setInfoCount(Integer infoCount) { this.infoCount = infoCount; }

    public Boolean getValid() { return valid; }
    public void setValid(Boolean valid) { this.valid = valid; }
}