package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.FiscalPeriodClosingException;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.TrialBalance;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodClosingQueryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodClosingValidator;
import com.globaldynamicssystems.aurum.accounting.service.TrialBalanceService;
import org.springframework.stereotype.Component;

@Component
public class DefaultFiscalPeriodClosingValidator implements FiscalPeriodClosingValidator {

    private final JournalEntryRepository journalEntryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final TrialBalanceService trialBalanceService;
    private final FiscalPeriodClosingQueryRepository queryRepository;

    public DefaultFiscalPeriodClosingValidator(
            JournalEntryRepository journalEntryRepository,
            LedgerEntryRepository ledgerEntryRepository,
            TrialBalanceService trialBalanceService,
            FiscalPeriodClosingQueryRepository queryRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.trialBalanceService = trialBalanceService;
        this.queryRepository = queryRepository;
    }

    @Override
    public void validate(FiscalPeriod fiscalPeriod) {
        if (fiscalPeriod == null) {
            throw new FiscalPeriodClosingException("FiscalPeriod cannot be null");
        }

        if (!FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus())) {
            throw new FiscalPeriodClosingException("FiscalPeriod must be in OPEN status to be closed");
        }

        Long periodId = fiscalPeriod.getId();

        long draftCount = queryRepository.countDraftEntries(periodId);
        if (draftCount > 0) {
            throw new FiscalPeriodClosingException("Cannot close fiscal period: exists " + draftCount + " DRAFT journal entry(ies)");
        }

        long validatedCount = queryRepository.countValidatedEntries(periodId);
        if (validatedCount > 0) {
            throw new FiscalPeriodClosingException("Cannot close fiscal period: exists " + validatedCount + " VALIDATED journal entry(ies)");
        }

        long unledgeredPostedCount = queryRepository.countPostedEntriesWithoutLedger(periodId);
        if (unledgeredPostedCount > 0) {
            throw new FiscalPeriodClosingException("Cannot close fiscal period: posting inconsistency detected. " + unledgeredPostedCount + " POSTED journal entry(ies) lack ledger records");
        }

        TrialBalance trialBalance = trialBalanceService.generateTrialBalance(periodId);
        if (trialBalance == null || !trialBalance.isBalanced()) {
            throw new FiscalPeriodClosingException("Cannot close fiscal period: Trial Balance is not balanced");
        }
    }

    public JournalEntryRepository getJournalEntryRepository() {
        return journalEntryRepository;
    }

    public LedgerEntryRepository getLedgerEntryRepository() {
        return ledgerEntryRepository;
    }

    public TrialBalanceService getTrialBalanceService() {
        return trialBalanceService;
    }

    public FiscalPeriodClosingQueryRepository getQueryRepository() {
        return queryRepository;
    }
}