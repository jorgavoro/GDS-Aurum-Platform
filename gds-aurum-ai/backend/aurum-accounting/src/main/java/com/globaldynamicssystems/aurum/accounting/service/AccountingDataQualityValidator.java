package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;

public interface AccountingDataQualityValidator {

    void validate(AccountingDataQualityReport report);
}
