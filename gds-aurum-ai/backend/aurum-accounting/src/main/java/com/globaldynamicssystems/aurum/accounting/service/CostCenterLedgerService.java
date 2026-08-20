package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface CostCenterLedgerService {

    List<LedgerEntry> findEntries(Long costCenterId, Long fiscalPeriodId);

    BigDecimal calculateDebit(Long costCenterId, Long fiscalPeriodId);

    BigDecimal calculateCredit(Long costCenterId, Long fiscalPeriodId);

    BigDecimal calculateBalance(Long costCenterId, Long fiscalPeriodId);
}