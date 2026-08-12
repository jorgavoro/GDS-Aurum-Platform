package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.model.TrialBalance;
import com.globaldynamicssystems.aurum.accounting.model.TrialBalanceLine;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.TrialBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DefaultTrialBalanceService implements TrialBalanceService {

    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public DefaultTrialBalanceService(ChartOfAccountsRepository chartOfAccountsRepository,
                                      FiscalPeriodRepository fiscalPeriodRepository,
                                      AccountRepository accountRepository,
                                      LedgerEntryRepository ledgerEntryRepository) {
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    public TrialBalance generate(Long chartOfAccountsId, Long fiscalPeriodId) {
        return generate(chartOfAccountsId, fiscalPeriodId, false);
    }

    @Override
    public TrialBalance generate(Long chartOfAccountsId, Long fiscalPeriodId, boolean includeNonPostableAccounts) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }

        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException("ChartOfAccounts not found with ID: " + chartOfAccountsId));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new IllegalArgumentException("FiscalPeriod not found with ID: " + fiscalPeriodId));

        if (fiscalPeriod.getChartOfAccounts() != null &&
            !chartOfAccountsId.equals(fiscalPeriod.getChartOfAccounts().getId())) {
            throw new IllegalArgumentException("FiscalPeriod with ID " + fiscalPeriodId +
                    " does not belong to ChartOfAccounts with ID " + chartOfAccountsId);
        }

        List<Account> accounts = accountRepository.findByChartOfAccountsId(chartOfAccountsId);
        List<LedgerEntry> ledgerEntries = ledgerEntryRepository.findByFiscalPeriodId(fiscalPeriodId);

        Map<Long, BigDecimal[]> accountTotalsMap = new HashMap<>();
        for (LedgerEntry entry : ledgerEntries) {
            if (entry != null && entry.getAccount() != null && entry.getAccount().getId() != null) {
                Long accountId = entry.getAccount().getId();
                BigDecimal[] totals = accountTotalsMap.computeIfAbsent(accountId, k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
                if (entry.getDebit() != null) {
                    totals[0] = totals[0].add(entry.getDebit());
                }
                if (entry.getCredit() != null) {
                    totals[1] = totals[1].add(entry.getCredit());
                }
            }
        }

        List<TrialBalanceLine> lines = new ArrayList<>();
        BigDecimal overallTotalDebit = BigDecimal.ZERO;
        BigDecimal overallTotalCredit = BigDecimal.ZERO;

        List<Account> sortedAccounts = new ArrayList<>(accounts);
        sortedAccounts.sort(Comparator.comparing(Account::getCode, Comparator.nullsLast(String::compareTo)));

        for (Account account : sortedAccounts) {
            boolean isPostable = Boolean.TRUE.equals(account.getPostable());
            if (!includeNonPostableAccounts && !isPostable) {
                continue;
            }

            BigDecimal[] totals = accountTotalsMap.getOrDefault(account.getId(), new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            BigDecimal accountDebit = totals[0];
            BigDecimal accountCredit = totals[1];
            BigDecimal accountBalance = accountDebit.subtract(accountCredit);

            TrialBalanceLine line = new TrialBalanceLine();
            line.setAccountId(account.getId());
            line.setAccountCode(account.getCode());
            line.setAccountName(account.getName());
            line.setAccountType(account.getAccountType());
            line.setNature(account.getNature());
            line.setDebit(accountDebit);
            line.setCredit(accountCredit);
            line.setBalance(accountBalance);
            line.setPostable(account.getPostable());

            lines.add(line);

            overallTotalDebit = overallTotalDebit.add(accountDebit);
            overallTotalCredit = overallTotalCredit.add(accountCredit);
        }

        BigDecimal overallTotalBalance = overallTotalDebit.subtract(overallTotalCredit);
        boolean isBalanced = overallTotalDebit.compareTo(overallTotalCredit) == 0;

        TrialBalance trialBalance = new TrialBalance();
        trialBalance.setChartOfAccountsId(chartOfAccounts.getId());
        trialBalance.setFiscalPeriodId(fiscalPeriod.getId());
        trialBalance.setChartOfAccountsCode(chartOfAccounts.getCode());
        trialBalance.setFiscalPeriodName(fiscalPeriod.getName());
        trialBalance.setLines(lines);
        trialBalance.setTotalDebit(overallTotalDebit);
        trialBalance.setTotalCredit(overallTotalCredit);
        trialBalance.setTotalBalance(overallTotalBalance);
        trialBalance.setBalanced(isBalanced);

        return trialBalance;
    }
}