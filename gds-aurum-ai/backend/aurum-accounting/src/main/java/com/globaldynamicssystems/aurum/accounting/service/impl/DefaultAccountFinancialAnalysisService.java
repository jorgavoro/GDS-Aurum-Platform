package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysis;
import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisFilter;
import com.globaldynamicssystems.aurum.accounting.model.AccountFinancialAnalysisReport;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisCalculator;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisService;
import com.globaldynamicssystems.aurum.accounting.service.AccountFinancialAnalysisValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultAccountFinancialAnalysisService implements AccountFinancialAnalysisService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final AccountFinancialAnalysisCalculator calculator;
    private final AccountFinancialAnalysisValidator validator;

    public DefaultAccountFinancialAnalysisService(AccountRepository accountRepository,
                                                  LedgerEntryRepository ledgerEntryRepository,
                                                  FiscalPeriodRepository fiscalPeriodRepository,
                                                  ChartOfAccountsRepository chartOfAccountsRepository,
                                                  AccountFinancialAnalysisCalculator calculator,
                                                  AccountFinancialAnalysisValidator validator) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.calculator = calculator;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountFinancialAnalysisReport analyze(Long chartOfAccountsId, Long fiscalPeriodId) {
        return analyze(new AccountFinancialAnalysisFilter(
                chartOfAccountsId, fiscalPeriodId, null, null, null, null, false));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountFinancialAnalysisReport analyze(Long chartOfAccountsId, Long fiscalPeriodId,
                                                  List<Long> accountIds) {
        return analyze(new AccountFinancialAnalysisFilter(
                chartOfAccountsId, fiscalPeriodId, accountIds, null, null, null, false));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountFinancialAnalysisReport analyze(AccountFinancialAnalysisFilter filter) {
        validateFilter(filter);

        chartOfAccountsRepository.findById(filter.getChartOfAccountsId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + filter.getChartOfAccountsId()));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(filter.getFiscalPeriodId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "FiscalPeriod not found with ID: " + filter.getFiscalPeriodId()));

        if (fiscalPeriod.getChartOfAccounts() == null
                || !filter.getChartOfAccountsId().equals(fiscalPeriod.getChartOfAccounts().getId())) {
            throw new IllegalArgumentException(
                    "FiscalPeriod does not belong to ChartOfAccounts: "
                            + filter.getChartOfAccountsId());
        }

        List<Account> accounts = resolveAccounts(filter);

        List<LedgerEntry> allEntries = ledgerEntryRepository
                .findByFiscalPeriodId(filter.getFiscalPeriodId());

        Map<Long, List<LedgerEntry>> entriesByAccountId = groupByAccountId(allEntries);

        List<AccountFinancialAnalysis> lines = new ArrayList<>();

        for (Account account : accounts) {
            List<LedgerEntry> entries = entriesByAccountId.getOrDefault(account.getId(), List.of());

            BigDecimal debit = calculator.calculateDebit(entries);
            BigDecimal credit = calculator.calculateCredit(entries);
            BigDecimal balance = calculator.calculateBalance(entries);
            BigDecimal revenue = calculator.calculateRevenue(entries);
            BigDecimal expense = calculator.calculateExpense(entries);
            BigDecimal netResult = calculator.calculateNetResult(revenue, expense);

            lines.add(new AccountFinancialAnalysis(
                    account.getId(),
                    account.getCode(),
                    account.getName(),
                    account.getAccountType(),
                    fiscalPeriod.getId(),
                    fiscalPeriod.getName(),
                    debit, credit, balance,
                    revenue, expense, netResult));
        }

        lines.sort(Comparator.comparing(AccountFinancialAnalysis::getAccountCode));

        BigDecimal totalDebit = lines.stream()
                .map(AccountFinancialAnalysis::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = lines.stream()
                .map(AccountFinancialAnalysis::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalBalance = totalDebit.subtract(totalCredit);
        BigDecimal totalRevenue = lines.stream()
                .map(AccountFinancialAnalysis::getRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpense = lines.stream()
                .map(AccountFinancialAnalysis::getExpense)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalNetResult = totalRevenue.subtract(totalExpense);

        AccountFinancialAnalysisReport report = new AccountFinancialAnalysisReport(
                filter.getChartOfAccountsId(),
                fiscalPeriod.getId(),
                fiscalPeriod.getName(),
                lines,
                totalDebit, totalCredit, totalBalance,
                totalRevenue, totalExpense, totalNetResult);

        validator.validate(report);

        return report;
    }

    private List<Account> resolveAccounts(AccountFinancialAnalysisFilter filter) {
        boolean includeInactive = Boolean.TRUE.equals(filter.getIncludeInactive());

        List<Account> accounts = includeInactive
                ? accountRepository.findByChartOfAccountsId(filter.getChartOfAccountsId())
                : accountRepository.findByChartOfAccountsIdAndActive(
                        filter.getChartOfAccountsId(), Boolean.TRUE);

        if (filter.getAccountIds() != null && !filter.getAccountIds().isEmpty()) {
            accounts = accounts.stream()
                    .filter(a -> filter.getAccountIds().contains(a.getId()))
                    .toList();
        }

        if (filter.getAccountTypes() != null && !filter.getAccountTypes().isEmpty()) {
            accounts = accounts.stream()
                    .filter(a -> filter.getAccountTypes().contains(a.getAccountType()))
                    .toList();
        }

        if (filter.getAccountCodeFrom() != null || filter.getAccountCodeTo() != null) {
            accounts = accounts.stream()
                    .filter(a -> isInCodeRange(a.getCode(),
                            filter.getAccountCodeFrom(), filter.getAccountCodeTo()))
                    .toList();
        }

        return accounts;
    }

    private boolean isInCodeRange(String code, String from, String to) {
        if (code == null) {
            return false;
        }
        if (from != null && code.compareTo(from) < 0) {
            return false;
        }
        if (to != null && code.compareTo(to) > 0) {
            return false;
        }
        return true;
    }

    private Map<Long, List<LedgerEntry>> groupByAccountId(List<LedgerEntry> entries) {
        Map<Long, List<LedgerEntry>> map = new LinkedHashMap<>();
        for (LedgerEntry entry : entries) {
            if (entry.getAccount() != null) {
                map.computeIfAbsent(entry.getAccount().getId(), k -> new ArrayList<>()).add(entry);
            }
        }
        return map;
    }

    private void validateFilter(AccountFinancialAnalysisFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("AccountFinancialAnalysisFilter cannot be null");
        }
        if (filter.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (filter.getFiscalPeriodId() == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }
    }
}
