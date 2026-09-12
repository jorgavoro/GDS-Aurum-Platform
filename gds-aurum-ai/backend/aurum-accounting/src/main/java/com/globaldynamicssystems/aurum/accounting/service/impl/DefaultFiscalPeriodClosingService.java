package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.FiscalPeriodClosingException;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodClosingCheckService;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodClosingService;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodClosingValidator;
import com.globaldynamicssystems.aurum.accounting.service.TrialBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultFiscalPeriodClosingService implements FiscalPeriodClosingService {

    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final TrialBalanceService trialBalanceService;
    private final FiscalPeriodClosingValidator fiscalPeriodClosingValidator;
    private final FiscalPeriodClosingCheckService fiscalPeriodClosingCheckService;

    public DefaultFiscalPeriodClosingService(
            FiscalPeriodRepository fiscalPeriodRepository,
            JournalEntryRepository journalEntryRepository,
            LedgerEntryRepository ledgerEntryRepository,
            TrialBalanceService trialBalanceService,
            FiscalPeriodClosingValidator fiscalPeriodClosingValidator,
            FiscalPeriodClosingCheckService fiscalPeriodClosingCheckService) {
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.journalEntryRepository = journalEntryRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.trialBalanceService = trialBalanceService;
        this.fiscalPeriodClosingValidator = fiscalPeriodClosingValidator;
        this.fiscalPeriodClosingCheckService = fiscalPeriodClosingCheckService;
    }

    @Override
    @Transactional
    public FiscalPeriod close(Long fiscalPeriodId) {
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("Fiscal period ID cannot be null");
        }

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new FiscalPeriodClosingException("Fiscal period not found with ID: " + fiscalPeriodId));

        if (FiscalPeriodStatus.CLOSED.equals(fiscalPeriod.getStatus())) {
            throw new IllegalStateException("Fiscal period is already closed: " + fiscalPeriodId);
        }

        if (!FiscalPeriodStatus.OPEN.equals(fiscalPeriod.getStatus())) {
            throw new FiscalPeriodClosingException("Fiscal period must be OPEN to be closed. Current status: " + fiscalPeriod.getStatus());
        }

        fiscalPeriodClosingValidator.validate(fiscalPeriod);

        fiscalPeriod.setStatus(FiscalPeriodStatus.CLOSED);

        return fiscalPeriodRepository.save(fiscalPeriod);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canClose(Long fiscalPeriodId) {
        if (fiscalPeriodId == null) {
            return false;
        }
        return fiscalPeriodClosingCheckService.check(fiscalPeriodId).isSuccessful();
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

    public FiscalPeriodClosingValidator getFiscalPeriodClosingValidator() {
        return fiscalPeriodClosingValidator;
    }

    public FiscalPeriodClosingCheckService getFiscalPeriodClosingCheckService() {
        return fiscalPeriodClosingCheckService;
    }
}