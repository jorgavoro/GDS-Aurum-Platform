package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.AccountingPeriodControlService;
import com.globaldynamicssystems.aurum.accounting.service.AccountingPeriodGuard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultAccountingPeriodGuard implements AccountingPeriodGuard {

    private final AccountingPeriodControlService accountingPeriodControlService;

    public DefaultAccountingPeriodGuard(AccountingPeriodControlService accountingPeriodControlService) {
        this.accountingPeriodControlService = accountingPeriodControlService;
    }

    @Override
    public void guardJournalEntry(Long fiscalPeriodId, LocalDate accountingDate) {
        accountingPeriodControlService.validateTransactionAllowed(fiscalPeriodId, accountingDate);
    }

    @Override
    public void guardPosting(Long fiscalPeriodId, LocalDate accountingDate) {
        accountingPeriodControlService.validateTransactionAllowed(fiscalPeriodId, accountingDate);
    }

    @Override
    public void guardOpeningBalance(Long fiscalPeriodId, LocalDate accountingDate) {
        accountingPeriodControlService.validateTransactionAllowed(fiscalPeriodId, accountingDate);
    }
}