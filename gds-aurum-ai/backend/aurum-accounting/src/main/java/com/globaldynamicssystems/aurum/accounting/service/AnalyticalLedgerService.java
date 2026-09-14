package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface AnalyticalLedgerService {

    List<LedgerEntry> findByDimension(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId);

    BigDecimal calculateDebit(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId);

    BigDecimal calculateCredit(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId);

    BigDecimal calculateBalance(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId);
}
