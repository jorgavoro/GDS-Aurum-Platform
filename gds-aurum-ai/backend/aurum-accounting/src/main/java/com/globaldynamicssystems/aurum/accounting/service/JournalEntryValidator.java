package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;

public interface JournalEntryValidator {

    void validate(JournalEntry journalEntry);
}