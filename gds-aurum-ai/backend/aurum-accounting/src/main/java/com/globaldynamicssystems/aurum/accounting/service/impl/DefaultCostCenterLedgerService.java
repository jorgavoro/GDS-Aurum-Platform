package com.globaldynamicssystems.aurum.accounting.service.impl;

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
        return ledgerEntryRepository.findByCostCenterIdAndFiscalPeriodId(costCenterId, fiscalPeriodId);
    }

    @Override
    public BigDecimal calculateDebit(Long costCenterId, Long fiscalPeriodId) {
        List<LedgerEntry> entries = findEntries(costCenterId, fiscalPeriodId);
        return entries.stream()
                .map(entry -> entry.getDebit() != null ? entry.getDebit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCredit(Long costCenterId, Long fiscalPeriodId) {
        List<LedgerEntry> entries = findEntries(costCenterId, fiscalPeriodId);
        return entries.stream()
                .map(entry -> entry.getCredit() != null ? entry.getCredit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateBalance(Long costCenterId, Long fiscalPeriodId) {
        BigDecimal totalDebit = calculateDebit(costCenterId, fiscalPeriodId);
        BigDecimal totalCredit = calculateCredit(costCenterId, fiscalPeriodId);
        return totalDebit.subtract(totalCredit);
    }
}