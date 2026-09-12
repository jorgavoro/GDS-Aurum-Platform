package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLineDimension;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryLineDimensionRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryLineRepository;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalDimensionService;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryDimensionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultJournalEntryDimensionService implements JournalEntryDimensionService {

    private final JournalEntryLineRepository journalEntryLineRepository;
    private final JournalEntryLineDimensionRepository dimensionRepository;
    private final AnalyticalDimensionService analyticalDimensionService;

    public DefaultJournalEntryDimensionService(JournalEntryLineRepository journalEntryLineRepository,
                                               JournalEntryLineDimensionRepository dimensionRepository,
                                               AnalyticalDimensionService analyticalDimensionService) {
        this.journalEntryLineRepository = journalEntryLineRepository;
        this.dimensionRepository = dimensionRepository;
        this.analyticalDimensionService = analyticalDimensionService;
    }

    @Override
    @Transactional
    public void addDimension(Long journalEntryLineId, AnalyticalDimensionType type, Long referenceId) {
        if (journalEntryLineId == null) {
            throw new IllegalArgumentException("JournalEntryLine ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("AnalyticalDimensionType cannot be null");
        }
        if (referenceId == null) {
            throw new IllegalArgumentException("referenceId cannot be null");
        }

        JournalEntryLine line = journalEntryLineRepository.findById(journalEntryLineId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "JournalEntryLine not found with ID: " + journalEntryLineId));

        AnalyticalDimensionValue dimensionValue = analyticalDimensionService.find(type, referenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AnalyticalDimensionValue not found for type " + type + " and referenceId " + referenceId));

        if (!Boolean.TRUE.equals(dimensionValue.getActive())) {
            throw new IllegalArgumentException(
                    "AnalyticalDimensionValue is not active: type=" + type + ", referenceId=" + referenceId);
        }

        boolean alreadyExists = dimensionRepository.findByJournalEntryLineId(journalEntryLineId)
                .stream()
                .anyMatch(d -> d.getDimension().getDimensionType() == type
                        && d.getDimension().getReferenceId().equals(referenceId));

        if (alreadyExists) {
            throw new IllegalArgumentException(
                    "Dimension already associated: type=" + type + ", referenceId=" + referenceId);
        }

        JournalEntryLineDimension association = new JournalEntryLineDimension();
        association.setJournalEntryLine(line);
        association.setDimension(dimensionValue);

        dimensionRepository.save(association);
    }

    @Override
    @Transactional
    public void removeDimension(Long journalEntryLineId, AnalyticalDimensionType type, Long referenceId) {
        if (journalEntryLineId == null) {
            throw new IllegalArgumentException("JournalEntryLine ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("AnalyticalDimensionType cannot be null");
        }
        if (referenceId == null) {
            throw new IllegalArgumentException("referenceId cannot be null");
        }

        dimensionRepository.findByJournalEntryLineId(journalEntryLineId)
                .stream()
                .filter(d -> d.getDimension().getDimensionType() == type
                        && d.getDimension().getReferenceId().equals(referenceId))
                .findFirst()
                .ifPresent(dimensionRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalEntryLineDimension> findDimensions(Long journalEntryLineId) {
        if (journalEntryLineId == null) {
            throw new IllegalArgumentException("JournalEntryLine ID cannot be null");
        }
        return dimensionRepository.findByJournalEntryLineId(journalEntryLineId);
    }
}
