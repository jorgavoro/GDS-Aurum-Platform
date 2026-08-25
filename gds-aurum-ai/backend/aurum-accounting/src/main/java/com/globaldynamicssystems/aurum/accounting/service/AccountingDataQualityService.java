package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountingDataQualityReport;

public interface AccountingDataQualityService {

    AccountingDataQualityReport validate(Long chartOfAccountsId, Long fiscalPeriodId);

    AccountingDataQualityReport validateChartOfAccounts(Long chartOfAccountsId);

    AccountingDataQualityReport validateJournalEntry(Long journalEntryId);

    AccountingDataQualityReport validateLedger(Long chartOfAccountsId, Long fiscalPeriodId);
}
