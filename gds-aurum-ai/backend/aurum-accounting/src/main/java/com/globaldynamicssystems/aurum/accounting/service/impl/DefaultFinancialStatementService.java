package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.FinancialStatementException;
import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.BalanceSheet;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FinancialStatementLine;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.IncomeStatement;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementCalculator;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementService;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialStatementService implements FinancialStatementService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final AccountRepository accountRepository;
    private final FinancialStatementCalculator calculator;
    private final FinancialStatementValidator validator;

    public DefaultFinancialStatementService(
            LedgerEntryRepository ledgerEntryRepository,
            ChartOfAccountsRepository chartOfAccountsRepository,
            FiscalPeriodRepository fiscalPeriodRepository,
            AccountRepository accountRepository,
            FinancialStatementCalculator calculator,
            FinancialStatementValidator validator) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.accountRepository = accountRepository;
        this.calculator = calculator;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public BalanceSheet generateBalanceSheet(Long chartOfAccountsId, Long fiscalPeriodId) {
        validateInputs(chartOfAccountsId, fiscalPeriodId);

        ChartOfAccounts coa = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new FinancialStatementException("Chart of Accounts not found: " + chartOfAccountsId));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new FinancialStatementException("Fiscal Period not found: " + fiscalPeriodId));

        validatePeriodBelongsToChartOfAccounts(fiscalPeriod, chartOfAccountsId);

        List<Account> accounts = getPostableAccountsSorted(chartOfAccountsId);
        List<LedgerEntry> periodEntries = ledgerEntryRepository.findByFiscalPeriodId(fiscalPeriodId);
        Map<Long, List<LedgerEntry>> entriesByAccount = groupEntriesByAccount(periodEntries);

        List<FinancialStatementLine> assets = new ArrayList<>();
        List<FinancialStatementLine> liabilities = new ArrayList<>();
        List<FinancialStatementLine> equity = new ArrayList<>();

        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;
        BigDecimal totalEquity = BigDecimal.ZERO;

        for (Account account : accounts) {
            if (account.getAccountType() == null) {
                continue;
            }

            List<LedgerEntry> accountEntries = entriesByAccount.getOrDefault(account.getId(), List.of());
            BigDecimal debitSum = sumDebits(accountEntries);
            BigDecimal creditSum = sumCredits(accountEntries);

            if (AccountType.ASSET.equals(account.getAccountType())) {
                BigDecimal balance = calculator.calculateAccountBalance(accountEntries);
                FinancialStatementLine line = createLine(account, debitSum, creditSum, balance);
                assets.add(line);
                totalAssets = totalAssets.add(balance);
            } else if (AccountType.LIABILITY.equals(account.getAccountType())) {
                BigDecimal balance = calculator.calculateAccountBalance(accountEntries);
                FinancialStatementLine line = createLine(account, debitSum, creditSum, balance);
                liabilities.add(line);
                totalLiabilities = totalLiabilities.add(balance);
            } else if (AccountType.EQUITY.equals(account.getAccountType())) {
                BigDecimal balance = calculator.calculateAccountBalance(accountEntries);
                FinancialStatementLine line = createLine(account, debitSum, creditSum, balance);
                equity.add(line);
                totalEquity = totalEquity.add(balance);
            }
        }

        BigDecimal liabilitiesAndEquity = totalLiabilities.add(totalEquity);
        boolean balanced = totalAssets.compareTo(liabilitiesAndEquity) == 0;

        BalanceSheet balanceSheet = new BalanceSheet(
                chartOfAccountsId,
                fiscalPeriodId,
                coa.getCode(),
                getFiscalPeriodDisplayName(fiscalPeriod),
                assets,
                liabilities,
                equity,
                totalAssets,
                totalLiabilities,
                totalEquity,
                liabilitiesAndEquity,
                balanced
        );

        validator.validateBalanceSheet(balanceSheet);
        return balanceSheet;
    }

    @Override
    @Transactional(readOnly = true)
    public IncomeStatement generateIncomeStatement(Long chartOfAccountsId, Long fiscalPeriodId) {
        validateInputs(chartOfAccountsId, fiscalPeriodId);

        ChartOfAccounts coa = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new FinancialStatementException("Chart of Accounts not found: " + chartOfAccountsId));

        FiscalPeriod fiscalPeriod = fiscalPeriodRepository.findById(fiscalPeriodId)
                .orElseThrow(() -> new FinancialStatementException("Fiscal Period not found: " + fiscalPeriodId));

        validatePeriodBelongsToChartOfAccounts(fiscalPeriod, chartOfAccountsId);

        List<Account> accounts = getPostableAccountsSorted(chartOfAccountsId);
        List<LedgerEntry> periodEntries = ledgerEntryRepository.findByFiscalPeriodId(fiscalPeriodId);
        Map<Long, List<LedgerEntry>> entriesByAccount = groupEntriesByAccount(periodEntries);

        List<FinancialStatementLine> revenues = new ArrayList<>();
        List<FinancialStatementLine> expenses = new ArrayList<>();

        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Account account : accounts) {
            if (account.getAccountType() == null) {
                continue;
            }

            List<LedgerEntry> accountEntries = entriesByAccount.getOrDefault(account.getId(), List.of());
            BigDecimal debitSum = sumDebits(accountEntries);
            BigDecimal creditSum = sumCredits(accountEntries);

            if (AccountType.REVENUE.equals(account.getAccountType())) {
                BigDecimal revenueAmount = calculator.calculateRevenue(accountEntries);
                FinancialStatementLine line = createLine(account, debitSum, creditSum, revenueAmount);
                revenues.add(line);
                totalRevenue = totalRevenue.add(revenueAmount);
            } else if (AccountType.EXPENSE.equals(account.getAccountType())) {
                BigDecimal expenseAmount = calculator.calculateExpense(accountEntries);
                FinancialStatementLine line = createLine(account, debitSum, creditSum, expenseAmount);
                expenses.add(line);
                totalExpense = totalExpense.add(expenseAmount);
            }
        }

        BigDecimal netIncome = totalRevenue.subtract(totalExpense);

        IncomeStatement incomeStatement = new IncomeStatement(
                chartOfAccountsId,
                fiscalPeriodId,
                coa.getCode(),
                getFiscalPeriodDisplayName(fiscalPeriod),
                revenues,
                expenses,
                totalRevenue,
                totalExpense,
                netIncome
        );

        validator.validateIncomeStatement(incomeStatement);
        return incomeStatement;
    }

    private void validateInputs(Long chartOfAccountsId, Long fiscalPeriodId) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("Chart of accounts ID cannot be null");
        }
        if (fiscalPeriodId == null) {
            throw new IllegalArgumentException("Fiscal period ID cannot be null");
        }
    }

    private void validatePeriodBelongsToChartOfAccounts(FiscalPeriod fiscalPeriod, Long chartOfAccountsId) {
        if (fiscalPeriod.getChartOfAccounts() != null
                && fiscalPeriod.getChartOfAccounts().getId() != null
                && !fiscalPeriod.getChartOfAccounts().getId().equals(chartOfAccountsId)) {
            throw new FinancialStatementException("Fiscal period " + fiscalPeriod.getId() + " does not belong to Chart of Accounts " + chartOfAccountsId);
        }
    }

    private List<Account> getPostableAccountsSorted(Long chartOfAccountsId) {
        List<Account> allAccounts = accountRepository.findByChartOfAccountsId(chartOfAccountsId);
        return allAccounts.stream()
                .filter(a -> a != null && Boolean.TRUE.equals(a.getPostable()))
                .sorted(Comparator.comparing(Account::getCode, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    private Map<Long, List<LedgerEntry>> groupEntriesByAccount(List<LedgerEntry> entries) {
        Map<Long, List<LedgerEntry>> map = new HashMap<>();
        if (entries != null) {
            for (LedgerEntry entry : entries) {
                if (entry != null && entry.getAccount() != null && entry.getAccount().getId() != null) {
                    map.computeIfAbsent(entry.getAccount().getId(), k -> new ArrayList<>()).add(entry);
                }
            }
        }
        return map;
    }

    private BigDecimal sumDebits(List<LedgerEntry> entries) {
        BigDecimal sum = BigDecimal.ZERO;
        if (entries != null) {
            for (LedgerEntry entry : entries) {
                if (entry != null && entry.getDebit() != null) {
                    sum = sum.add(entry.getDebit());
                }
            }
        }
        return sum;
    }

    private BigDecimal sumCredits(List<LedgerEntry> entries) {
        BigDecimal sum = BigDecimal.ZERO;
        if (entries != null) {
            for (LedgerEntry entry : entries) {
                if (entry != null && entry.getCredit() != null) {
                    sum = sum.add(entry.getCredit());
                }
            }
        }
        return sum;
    }

    private FinancialStatementLine createLine(Account account, BigDecimal debit, BigDecimal credit, BigDecimal balance) {
        return new FinancialStatementLine(
                account.getId(),
                account.getCode(),
                account.getName(),
                account.getAccountType(),
                account.getNature(),
                debit,
                credit,
                balance
        );
    }

    private String getFiscalPeriodDisplayName(FiscalPeriod fiscalPeriod) {
        if (fiscalPeriod == null) {
            return "Unknown Period";
        }
        if (fiscalPeriod.getName() != null && !fiscalPeriod.getName().isBlank()) {
            return fiscalPeriod.getName();
        }
        return "Period-" + fiscalPeriod.getId();
    }

    public LedgerEntryRepository getLedgerEntryRepository() {
        return ledgerEntryRepository;
    }

    public ChartOfAccountsRepository getChartOfAccountsRepository() {
        return chartOfAccountsRepository;
    }

    public FiscalPeriodRepository getFiscalPeriodRepository() {
        return fiscalPeriodRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public FinancialStatementCalculator getCalculator() {
        return calculator;
    }

    public FinancialStatementValidator getValidator() {
        return validator;
    }
}