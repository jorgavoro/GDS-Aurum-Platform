package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryService;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DefaultJournalEntryService implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final JournalEntryValidator journalEntryValidator;

    public DefaultJournalEntryService(JournalEntryRepository journalEntryRepository,
                                      ChartOfAccountsRepository chartOfAccountsRepository,
                                      FiscalPeriodRepository fiscalPeriodRepository,
                                      JournalEntryValidator journalEntryValidator) {
        this.journalEntryRepository = journalEntryRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.journalEntryValidator = journalEntryValidator;
    }

    @Override
    public JournalEntry create(JournalEntry journalEntry) {
        if (journalEntry == null) {
            throw new IllegalArgumentException("JournalEntry cannot be null");
        }
        if (journalEntry.getDocumentNumber() == null || journalEntry.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number cannot be null or empty");
        }
        if (journalEntryRepository.existsByDocumentNumber(journalEntry.getDocumentNumber())) {
            throw new IllegalArgumentException("Document number must be unique: " + journalEntry.getDocumentNumber());
        }
        if (journalEntry.getChartOfAccounts() == null || journalEntry.getChartOfAccounts().getId() == null) {
            throw new IllegalArgumentException("ChartOfAccounts cannot be null");
        }
        if (journalEntry.getFiscalPeriod() == null || journalEntry.getFiscalPeriod().getId() == null) {
            throw new IllegalArgumentException("FiscalPeriod cannot be null");
        }

        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(journalEntry.getChartOfAccounts().getId())
                .orElseThrow(() -> new IllegalArgumentException("ChartOfAccounts not found with ID: " 
                        + journalEntry.getChartOfAccounts().getId()));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(journalEntry.getFiscalPeriod().getId())
                .orElseThrow(() -> new IllegalArgumentException("FiscalPeriod not found with ID: " 
                        + journalEntry.getFiscalPeriod().getId()));

        journalEntry.setChartOfAccounts(chartOfAccounts);
        journalEntry.setFiscalPeriod(fiscalPeriod);

        if (journalEntry.getLines() != null) {
            for (JournalEntryLine line : journalEntry.getLines()) {
                line.setJournalEntry(journalEntry);
            }
        }

        journalEntry.setStatus(JournalEntryStatus.DRAFT);

        journalEntryValidator.validate(journalEntry);

        journalEntry.setStatus(JournalEntryStatus.VALIDATED);

        return journalEntryRepository.save(journalEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JournalEntry> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return journalEntryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JournalEntry> findByDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            return Optional.empty();
        }
        return journalEntryRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    public JournalEntry validate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("JournalEntry ID cannot be null");
        }

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JournalEntry not found with ID: " + id));

        journalEntryValidator.validate(journalEntry);

        journalEntry.setStatus(JournalEntryStatus.VALIDATED);

        return journalEntryRepository.save(journalEntry);
    }
}