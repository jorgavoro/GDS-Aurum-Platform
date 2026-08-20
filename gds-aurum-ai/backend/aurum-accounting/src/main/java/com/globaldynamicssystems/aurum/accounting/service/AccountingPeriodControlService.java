package com.globaldynamicssystems.aurum.accounting.service;

import java.time.LocalDate;

public interface AccountingPeriodControlService {

    boolean isOpen(Long fiscalPeriodId);

    boolean isClosed(Long fiscalPeriodId);

    void validateOpen(Long fiscalPeriodId);

    void validateAccountingDate(Long fiscalPeriodId, LocalDate accountingDate);

    void validateTransactionAllowed(Long fiscalPeriodId, LocalDate accountingDate);
}