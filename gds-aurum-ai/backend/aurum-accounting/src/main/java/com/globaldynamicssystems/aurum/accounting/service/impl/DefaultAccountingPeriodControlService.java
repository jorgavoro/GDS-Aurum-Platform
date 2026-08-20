package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.AccountingPeriodClosedException;
import com.globaldynamicssystems.aurum.accounting.exception.AccountingPeriodValidationException;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.service.AccountingPeriodControlService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultAccountingPeriodControlService implements AccountingPeriodControlService {

    private final FiscalPeriodRepository fiscalPeriodRepository;

    public DefaultAccountingPeriodControlService(FiscalPeriodRepository fiscalPeriodRepository) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
    }

    @Override
    public boolean isOpen(Long fiscalPeriodId) {
        FiscalPeriod fiscalPeriod = findFiscalPeriodById(fiscalPeriodId);
        return FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus());
    }

    @Override
    public boolean isClosed(Long fiscalPeriodId) {
        FiscalPeriod fiscalPeriod = findFiscalPeriodById(fiscalPeriodId);
        return FiscalPeriodStatus.CLOSED.equals(fiscalPeriod.getStatus());
    }

    @Override
    public void validateOpen(Long fiscalPeriodId) {
        FiscalPeriod fiscalPeriod = findFiscalPeriodById(fiscalPeriodId);
        if (FiscalPeriodStatus.CLOSED.equals(fiscalPeriod.getStatus())) {
            throw new AccountingPeriodClosedException(
                    "Fiscal period with ID " + fiscalPeriodId + " is CLOSED. Operations are not allowed."
            );
        }
    }

    @Override
    public void validateAccountingDate(Long fiscalPeriodId, LocalDate accountingDate) {
        if (accountingDate == null) {
            throw new AccountingPeriodValidationException("Accounting date cannot be null.");
        }

        FiscalPeriod fiscalPeriod = findFiscalPeriodById(fiscalPeriodId);
        LocalDate startDate = fiscalPeriod.getStartDate();
        LocalDate endDate = fiscalPeriod.getEndDate();

        if (accountingDate.isBefore(startDate) || accountingDate.isAfter(endDate)) {
            throw new AccountingPeriodValidationException(
                    "Accounting date " + accountingDate + " is outside the range of fiscal period "
                            + fiscalPeriodId + " [" + startDate + " - " + endDate + "]."
            );
        }
    }

    @Override
    public void validateTransactionAllowed(Long fiscalPeriodId, LocalDate accountingDate) {
        validateOpen(fiscalPeriodId);
        validateAccountingDate(fiscalPeriodId, accountingDate);
    }

    private FiscalPeriod findFiscalPeriodById(Long fiscalPeriodId) {
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("Fiscal period ID cannot be null.");
        }
        return fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new IllegalArgumentException("Fiscal period not found with ID: " + fiscalPeriodId));
    }
}