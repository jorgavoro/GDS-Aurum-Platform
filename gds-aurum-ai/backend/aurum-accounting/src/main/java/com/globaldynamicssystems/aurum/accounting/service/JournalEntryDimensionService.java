package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLineDimension;

import java.util.List;

public interface JournalEntryDimensionService {

    void addDimension(Long journalEntryLineId, AnalyticalDimensionType type, Long referenceId);

    void removeDimension(Long journalEntryLineId, AnalyticalDimensionType type, Long referenceId);

    List<JournalEntryLineDimension> findDimensions(Long journalEntryLineId);
}
