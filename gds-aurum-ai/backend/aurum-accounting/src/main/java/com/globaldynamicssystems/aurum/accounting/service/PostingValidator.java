package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;

public interface PostingValidator {

    void validate(JournalEntry journalEntry);
}