package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.PostingException;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryValidator;
import com.globaldynamicssystems.aurum.accounting.service.PostingService;
import com.globaldynamicssystems.aurum.accounting.service.PostingValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultPostingService implements PostingService {

    private final JournalEntryRepository journalEntryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final JournalEntryValidator journalEntryValidator;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final PostingValidator postingValidator;

    public DefaultPostingService(JournalEntryRepository journalEntryRepository,
                                 LedgerEntryRepository ledgerEntryRepository,
                                 JournalEntryValidator journalEntryValidator,
                                 FiscalPeriodRepository fiscalPeriodRepository,
                                 PostingValidator postingValidator) {
        this.journalEntryRepository = journalEntryRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.journalEntryValidator = journalEntryValidator;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.postingValidator = postingValidator;
    }

    @Override
    @Transactional
    public JournalEntry post(Long journalEntryId) {
        if (journalEntryId == null) {
            throw new IllegalArgumentException("JournalEntry ID cannot be null");
        }

        if (ledgerEntryRepository.existsByJournalEntryId(journalEntryId)) {
            throw new IllegalStateException("JournalEntry with ID " + journalEntryId + " has already been posted");
        }

        JournalEntry journalEntry = journalEntryRepository.findById(journalEntryId)
                .orElseThrow(() -> new IllegalArgumentException("JournalEntry not found with ID: " + journalEntryId));

        if (JournalEntryStatus.POSTED.equals(journalEntry.getStatus())) {
            throw new IllegalStateException("JournalEntry is already in POSTED status: " + journalEntryId);
        }

        if (!JournalEntryStatus.VALIDATED.equals(journalEntry.getStatus())) {
            throw new IllegalStateException("JournalEntry must be in VALIDATED status to be posted. Current status: " 
                    + journalEntry.getStatus());
        }

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(journalEntry.getFiscalPeriod().getId())
                .orElseThrow(() -> new IllegalArgumentException("FiscalPeriod not found with ID: " 
                        + journalEntry.getFiscalPeriod().getId()));

        if (!FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus())) {
            throw new PostingException("Cannot post JournalEntry because FiscalPeriod is not OPEN");
        }

        journalEntryValidator.validate(journalEntry);

        postingValidator.validate(journalEntry);

        for (JournalEntryLine line : journalEntry.getLines()) {
            LedgerEntry ledgerEntry = new LedgerEntry();
            ledgerEntry.setJournalEntry(journalEntry);
            ledgerEntry.setJournalEntryLine(line);
            ledgerEntry.setAccount(line.getAccount());
            ledgerEntry.setFiscalPeriod(journalEntry.getFiscalPeriod());
            ledgerEntry.setAccountingDate(journalEntry.getAccountingDate());
            ledgerEntry.setDebit(line.getDebit());
            ledgerEntry.setCredit(line.getCredit());
            ledgerEntry.setDescription(line.getDescription() != null ? line.getDescription() : journalEntry.getDescription());
            ledgerEntry.setLineNumber(line.getLineNumber());

            ledgerEntryRepository.save(ledgerEntry);
        }

        journalEntry.setStatus(JournalEntryStatus.POSTED);

        return journalEntryRepository.save(journalEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPosted(Long journalEntryId) {
        if (journalEntryId == null) {
            return false;
        }
        return ledgerEntryRepository.existsByJournalEntryId(journalEntryId);
    }
}