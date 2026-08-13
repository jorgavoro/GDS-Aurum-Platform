package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodClosingResult;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.TrialBalance;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodClosingQueryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodClosingCheckService;
import com.globaldynamicssystems.aurum.accounting.service.TrialBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultFiscalPeriodClosingCheckService implements FiscalPeriodClosingCheckService {

    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final TrialBalanceService trialBalanceService;
    private final FiscalPeriodClosingQueryRepository queryRepository;

    public DefaultFiscalPeriodClosingCheckService(
            FiscalPeriodRepository fiscalPeriodRepository,
            JournalEntryRepository journalEntryRepository,
            LedgerEntryRepository ledgerEntryRepository,
            TrialBalanceService trialBalanceService,
            FiscalPeriodClosingQueryRepository queryRepository) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.journalEntryRepository = journalEntryRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.trialBalanceService = trialBalanceService;
        this.queryRepository = queryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FiscalPeriodClosingResult check(Long fiscalPeriodId) {
        FiscalPeriodClosingResult result = new FiscalPeriodClosingResult();
        result.setFiscalPeriodId(fiscalPeriodId);

        if (fiscalPeriodId == null) {
            result.setSuccessful(false);
            result.setMessage("Fiscal period ID cannot be null");
            return result;
        }

        Optional<FiscalPeriod> periodOpt = fiscalPeriodRepository.findById(fiscalPeriodId);
        if (periodOpt.isEmpty()) {
            result.setSuccessful(false);
            result.setMessage("Fiscal period not found with ID: " + fiscalPeriodId);
            return result;
        }

        FiscalPeriod fiscalPeriod = periodOpt.get();
        if (!FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus())) {
            result.setSuccessful(false);
            result.setMessage("Fiscal period is not OPEN. Current status: " + fiscalPeriod.getStatus());
            return result;
        }

        boolean hasDraft = queryRepository.countDraftEntries(fiscalPeriodId) > 0;
        boolean hasValidated = queryRepository.countValidatedEntries(fiscalPeriodId) > 0;
        boolean hasPostingInconsistencies = queryRepository.countPostedEntriesWithoutLedger(fiscalPeriodId) > 0;

        TrialBalance trialBalance = trialBalanceService.generateTrialBalance(fiscalPeriodId);
        boolean balanced = trialBalance != null && trialBalance.isBalanced();

        result.setHasDraftEntries(hasDraft);
        result.setHasValidatedEntries(hasValidated);
        result.setHasPostingInconsistencies(hasPostingInconsistencies);
        result.setBalanced(balanced);

        boolean successful = !hasDraft && !hasValidated && !hasPostingInconsistencies && balanced;
        result.setSuccessful(successful);

        if (successful) {
            result.setMessage("Fiscal period is ready to be closed");
        } else {
            result.setMessage("Fiscal period closing check failed due to pending items or unbalancing");
        }

        return result;
    }

    public FiscalPeriodRepository getFiscalPeriodRepository() {
        return fiscalPeriodRepository;
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