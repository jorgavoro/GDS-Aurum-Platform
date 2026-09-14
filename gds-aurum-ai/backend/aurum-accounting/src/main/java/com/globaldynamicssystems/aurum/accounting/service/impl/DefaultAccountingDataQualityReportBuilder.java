package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;
import com.globaldynamicssystems.aurum.accounting.model.DataQualityFinding;
import com.globaldynamicssystems.aurum.accounting.model.DataQualitySeverity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultAccountingDataQualityReportBuilder {

    public AccountingDataQualityReport build(Long chartOfAccountsId,
                                             Long fiscalPeriodId,
                                             List<DataQualityFinding> findings) {
        int errorCount = 0;
        int warningCount = 0;
        int infoCount = 0;
        String fiscalPeriodName = null;

        for (DataQualityFinding finding : findings) {
            if (DataQualitySeverity.ERROR.equals(finding.getSeverity())) {
                errorCount++;
            } else if (DataQualitySeverity.WARNING.equals(finding.getSeverity())) {
                warningCount++;
            } else if (DataQualitySeverity.INFO.equals(finding.getSeverity())) {
                infoCount++;
            }
        }

        boolean valid = errorCount == 0;

        return new AccountingDataQualityReport(
                chartOfAccountsId,
                fiscalPeriodId,
                fiscalPeriodName,
                findings,
                errorCount,
                warningCount,
                infoCount,
                valid);
    }
}
