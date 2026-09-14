package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;
import com.globaldynamicssystems.aurum.accounting.service.AccountingDataQualityValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultAccountingDataQualityValidator implements AccountingDataQualityValidator {

    @Override
    public void validate(AccountingDataQualityReport report) {
        if (report == null) {
            throw new IllegalStateException("AccountingDataQualityReport cannot be null");
        }
        if (report.getFindings() == null) {
            throw new IllegalStateException("findings cannot be null");
        }
        if (report.getErrorCount() == null) {
            throw new IllegalStateException("errorCount cannot be null");
        }
        if (report.getWarningCount() == null) {
            throw new IllegalStateException("warningCount cannot be null");
        }
        if (report.getInfoCount() == null) {
            throw new IllegalStateException("infoCount cannot be null");
        }
        if (report.getValid() == null) {
            throw new IllegalStateException("valid cannot be null");
        }
        if (report.getErrorCount() < 0) {
            throw new IllegalStateException("errorCount must be >= 0");
        }
        if (report.getWarningCount() < 0) {
            throw new IllegalStateException("warningCount must be >= 0");
        }
        if (report.getInfoCount() < 0) {
            throw new IllegalStateException("infoCount must be >= 0");
        }

        boolean expectedValid = report.getErrorCount() == 0;
        if (!expectedValid == report.getValid()) {
            throw new IllegalStateException(
                    "valid must be true when errorCount == 0 and false when errorCount > 0");
        }
    }
}
