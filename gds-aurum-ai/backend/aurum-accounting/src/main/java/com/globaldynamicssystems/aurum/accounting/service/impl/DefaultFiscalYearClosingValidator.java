package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalYearClosingRepository;
import com.globaldynamicssystems.aurum.accounting.service.FiscalYearClosingValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultFiscalYearClosingValidator implements FiscalYearClosingValidator {

    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final AccountRepository accountRepository;
    private final FiscalYearClosingRepository fiscalYearClosingRepository;

    public DefaultFiscalYearClosingValidator(
            ChartOfAccountsRepository chartOfAccountsRepository,
            AccountRepository accountRepository,
            FiscalYearClosingRepository fiscalYearClosingRepository
    ) {
        this.chartOfAccountsRepository = Objects.requireNonNull(chartOfAccountsRepository, "chartOfAccountsRepository must not be null");
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository must not be null");
        this.fiscalYearClosingRepository = Objects.requireNonNull(fiscalYearClosingRepository, "fiscalYearClosingRepository must not be null");
    }

    @Override
    public void validate(Long chartOfAccountsId, Integer fiscalYear, Long retainedEarningsAccountId) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId must not be null");
        }
        if (fiscalYear == null) {
            throw new IllegalArgumentException("fiscalYear must not be null");
        }
        if (retainedEarningsAccountId == null) {
            throw new IllegalArgumentException("retainedEarningsAccountId must not be null");
        }

        // 1. ChartOfAccounts existe
        if (!chartOfAccountsRepository.existsById(chartOfAccountsId)) {
            throw new IllegalArgumentException("ChartOfAccounts not found for id: " + chartOfAccountsId);
        }

        // 2. Retained Earnings Account existe
        Account retainedEarningsAccount = accountRepository.findById(retainedEarningsAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Retained Earnings account not found for id: " + retainedEarningsAccountId));

        // 3. Retained Earnings pertenece al ChartOfAccounts
        Long accountCoaId = getChartOfAccountsId(retainedEarningsAccount);
        if (accountCoaId != null && !accountCoaId.equals(chartOfAccountsId)) {
            throw new IllegalArgumentException("Retained Earnings account does not belong to ChartOfAccounts id: " + chartOfAccountsId);
        }

        // 4. Retained Earnings está activa
        if (!isAccountActive(retainedEarningsAccount)) {
            throw new IllegalArgumentException("Retained Earnings account is not active");
        }

        // 5. Retained Earnings es postable
        if (!isAccountPostable(retainedEarningsAccount)) {
            throw new IllegalArgumentException("Retained Earnings account is not postable");
        }

        // 6. Retained Earnings es de tipo EQUITY
        if (!isEquityAccount(retainedEarningsAccount)) {
            throw new IllegalArgumentException("Retained Earnings account type must be EQUITY");
        }

        // 7. Existen períodos para el ejercicio
        long totalPeriods = fiscalYearClosingRepository.countPeriodsByFiscalYear(chartOfAccountsId, fiscalYear);
        if (totalPeriods == 0) {
            throw new IllegalArgumentException("No fiscal periods found for fiscal year: " + fiscalYear);
        }

        // 8. Todos los períodos están CLOSED
        long closedPeriods = fiscalYearClosingRepository.countClosedPeriodsByFiscalYear(chartOfAccountsId, fiscalYear);
        if (closedPeriods < totalPeriods) {
            throw new IllegalStateException("Not all fiscal periods are CLOSED for fiscal year: " + fiscalYear);
        }

        // 9. No existen JournalEntries en DRAFT
        if (fiscalYearClosingRepository.countDraftJournalEntries(chartOfAccountsId, fiscalYear) > 0) {
            throw new IllegalStateException("Cannot close fiscal year: DRAFT journal entries exist for fiscal year: " + fiscalYear);
        }

        // 10. No existen JournalEntries en VALIDATED
        if (fiscalYearClosingRepository.countValidatedJournalEntries(chartOfAccountsId, fiscalYear) > 0) {
            throw new IllegalStateException("Cannot close fiscal year: VALIDATED journal entries exist for fiscal year: " + fiscalYear);
        }

        // 11. No existe previamente un asiento de cierre anual para el ejercicio
        if (fiscalYearClosingRepository.existsClosingEntry(chartOfAccountsId, fiscalYear)) {
            throw new IllegalStateException("Year end closing already executed for fiscal year: " + fiscalYear);
        }
    }

    private Long getChartOfAccountsId(Account account) {
        if (account.getChartOfAccounts() != null) {
            return account.getChartOfAccounts().getId();
        }
        return account.getChartOfAccountsId();
    }

    private boolean isAccountActive(Account account) {
        Boolean active = account.isActive();
        if (active != null) {
            return active;
        }
        active = account.isActive();
        if (active != null) {
            return active;
        }
        if (account.getStatus() != null) {
            return "ACTIVE".equalsIgnoreCase(account.getStatus());
        }
        return true;
    }

    private boolean isAccountPostable(Account account) {
        Boolean postable = account.isPostable();
        if (postable != null) {
            return postable;
        }
        postable = account.isPostable();
        if (postable != null) {
            return postable;
        }
        postable = account.getPostable();
        if (postable != null) {
            return postable;
        }
        return true;
    }

    private boolean isEquityAccount(Account account) {
        AccountType type = account.getAccountType();
        if (type != null) {
            return type == AccountType.EQUITY || "EQUITY".equalsIgnoreCase(type.name());
        }
        return false;
    }
}