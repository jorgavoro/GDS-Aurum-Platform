package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;

import java.util.Optional;

public interface JournalEntryService {

    JournalEntry create(JournalEntry journalEntry);

    Optional<JournalEntry> findById(Long id);

    Optional<JournalEntry> findByDocumentNumber(String documentNumber);

    JournalEntry validate(Long id);
}