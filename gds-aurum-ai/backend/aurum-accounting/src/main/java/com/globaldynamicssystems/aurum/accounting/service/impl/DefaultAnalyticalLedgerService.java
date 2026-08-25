package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalLedgerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultAnalyticalLedgerService implements AnalyticalLedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public DefaultAnalyticalLedgerService(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LedgerEntry> findByDimension(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId) {
        if (type == null) {
            throw new IllegalArgumentException("AnalyticalDimensionType cannot be null");
        }
        if (referenceId == null) {
            throw new IllegalArgumentException("referenceId cannot be null");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }
        return ledgerEntryRepository.findByDimensionTypeAndReferenceIdAndFiscalPeriodId(type, referenceId, fiscalPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDebit(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId) {
        return findByDimension(type, referenceId, fiscalPeriodId).stream()
                .map(e -> e.getDebit() != null ? e.getDebit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCredit(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId) {
        return findByDimension(type, referenceId, fiscalPeriodId).stream()
                .map(e -> e.getCredit() != null ? e.getCredit() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateBalance(AnalyticalDimensionType type, Long referenceId, Long fiscalPeriodId) {
        return calculateDebit(type, referenceId, fiscalPeriodId)
                .subtract(calculateCredit(type, referenceId, fiscalPeriodId));
    }
}
