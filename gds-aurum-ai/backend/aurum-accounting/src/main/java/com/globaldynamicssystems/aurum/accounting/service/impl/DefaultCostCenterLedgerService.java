package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.CostCenterRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.CostCenterLedgerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultCostCenterLedgerService implements CostCenterLedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final CostCenterRepository costCenterRepository;

    public DefaultCostCenterLedgerService(LedgerEntryRepository ledgerEntryRepository,
                                           CostCenterRepository costCenterRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.costCenterRepository = costCenterRepository;
    }

    @Override
    public List<LedgerEntry> findEntries(Long costCenterId, Long fiscalPeriodId) {
        if (costCenterId == null) {
            throw new IllegalArgumentException("CostCenter ID cannot be null.");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("FiscalPeriod ID cannot be null.");
        }
        if (!costCenterRepository.existsById(costCenterId)) {
            throw new IllegalArgumentException("CostCenter not found with ID: " + costCenterId);
        }
        return ledgerEntryRepository.findByDimensionTypeAndReferenceIdAndFiscalPeriodId(
                AnalyticalDimensionType.COST_CENTER, costCenterId, fiscalPeriodId);
    }

    @Override
    public BigDecimal calculateDebit(Long costCenterId, Long fiscalPeriodId) {
        return findEntries(costCenterId, fiscalPeriodId).stream()
                .map(e -> e.getDebit() != null ? e.getDebit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCredit(Long costCenterId, Long fiscalPeriodId) {
        return findEntries(costCenterId, fiscalPeriodId).stream()
                .map(e -> e.getCredit() != null ? e.getCredit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateBalance(Long costCenterId, Long fiscalPeriodId) {
        return calculateDebit(costCenterId, fiscalPeriodId)
                .subtract(calculateCredit(costCenterId, fiscalPeriodId));
    }
}