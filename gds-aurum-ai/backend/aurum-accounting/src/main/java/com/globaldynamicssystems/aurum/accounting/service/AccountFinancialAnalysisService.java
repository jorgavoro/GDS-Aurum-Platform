package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisFilter;
import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisReport;

import java.util.List;

public interface AccountFinancialAnalysisService {

    AccountFinancialAnalysisReport analyze(Long chartOfAccountsId, Long fiscalPeriodId);

    AccountFinancialAnalysisReport analyze(Long chartOfAccountsId, Long fiscalPeriodId,
                                           List<Long> accountIds);

    AccountFinancialAnalysisReport analyze(AccountFinancialAnalysisFilter filter);
}
