package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;

public interface PostingService {

    JournalEntry post(Long journalEntryId);

    boolean isPosted(Long journalEntryId);
}