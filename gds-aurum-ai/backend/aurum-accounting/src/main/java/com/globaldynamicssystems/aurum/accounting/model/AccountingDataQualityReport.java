package com.globaldynamicssystems.aurum.accounting.model;

import java.util.List;

public class AccountingDataQualityReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private List<DataQualityFinding> findings;
    private Integer errorCount;
    private Integer warningCount;
    private Integer infoCount;
    private Boolean valid;
    private String fiscalPeriodName;

    public AccountingDataQualityReport() {
    }

    public AccountingDataQualityReport(Long chartOfAccountsId,
            Long fiscalPeriodId,
            String fiscalPeriodName,
            List<DataQualityFinding> findings,
            Integer errorCount,
            Integer warningCount,
            Integer infoCount,
            Boolean valid) {
this.chartOfAccountsId = chartOfAccountsId;
this.fiscalPeriodId = fiscalPeriodId;
this.fiscalPeriodName = fiscalPeriodName;
this.findings = findings;
this.errorCount = errorCount;
this.warningCount = warningCount;
this.infoCount = infoCount;
this.valid = valid;
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
    
    public String getFiscalPeriodName() { return fiscalPeriodName; }
    public void setFiscalPeriodName(String fiscalPeriodName) { this.fiscalPeriodName = fiscalPeriodName; }
}
