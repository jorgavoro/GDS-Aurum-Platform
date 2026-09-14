package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowActivityType;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowFilter;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowLine;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowCalculator;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowClassificationService;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowService;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultCashFlowService implements CashFlowService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final CashFlowClassificationService classificationService;
    private final CashFlowCalculator calculator;
    private final CashFlowValidator validator;

    public DefaultCashFlowService(AccountRepository accountRepository,
                                  LedgerEntryRepository ledgerEntryRepository,
                                  FiscalPeriodRepository fiscalPeriodRepository,
                                  ChartOfAccountsRepository chartOfAccountsRepository,
                                  CashFlowClassificationService classificationService,
                                  CashFlowCalculator calculator,
                                  CashFlowValidator validator) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.fiscalPeriodRepository = fiscalPeriodRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.classificationService = classificationService;
        this.calculator = calculator;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public CashFlowReport generate(Long chartOfAccountsId, Long fiscalPeriodId) {
        return generate(new CashFlowFilter(chartOfAccountsId, fiscalPeriodId, null, null, false));
    }

    @Override
    @Transactional(readOnly = true)
    public CashFlowReport generate(CashFlowFilter filter) {
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

        List<LedgerEntry> entries = ledgerEntryRepository
                .findByChartOfAccountsIdAndFiscalPeriodId(
                        filter.getChartOfAccountsId(), filter.getFiscalPeriodId());

        if (filter.getAccountIds() != null && !filter.getAccountIds().isEmpty()) {
            entries = entries.stream()
                    .filter(e -> e.getAccount() != null
                            && filter.getAccountIds().contains(e.getAccount().getId()))
                    .toList();
        }

        Map<Long, Account> accountsById = buildAccountIndex(
                filter.getChartOfAccountsId(), filter.getIncludeInactive());

        Map<Long, List<LedgerEntry>> entriesByAccountId = groupByAccountId(entries);

        List<CashFlowLine> operatingLines = new ArrayList<>();
        List<CashFlowLine> investingLines = new ArrayList<>();
        List<CashFlowLine> financingLines = new ArrayList<>();

        for (Map.Entry<Long, List<LedgerEntry>> entry : entriesByAccountId.entrySet()) {
            Long accountId = entry.getKey();
            List<LedgerEntry> accountEntries = entry.getValue();

            Account account = accountsById.get(accountId);
            if (account == null) {
                continue;
            }

            CashFlowActivityType activityType = classificationService.classify(account);

            if (filter.getActivityTypes() != null && !filter.getActivityTypes().isEmpty()
                    && !filter.getActivityTypes().contains(activityType)) {
                continue;
            }

            BigDecimal[] inflowOutflow = deriveInflowOutflow(account, accountEntries);
            BigDecimal cashInflow = inflowOutflow[0];
            BigDecimal cashOutflow = inflowOutflow[1];
            BigDecimal netCashFlow = cashInflow.subtract(cashOutflow);

            CashFlowLine line = new CashFlowLine(
                    account.getId(),
                    account.getCode(),
                    account.getName(),
                    activityType,
                    cashInflow,
                    cashOutflow,
                    netCashFlow);

            switch (activityType) {
                case OPERATING -> operatingLines.add(line);
                case INVESTING -> investingLines.add(line);
                case FINANCING -> financingLines.add(line);
            }
        }

        operatingLines.sort(Comparator.comparing(CashFlowLine::getAccountCode));
        investingLines.sort(Comparator.comparing(CashFlowLine::getAccountCode));
        financingLines.sort(Comparator.comparing(CashFlowLine::getAccountCode));

        BigDecimal operatingCashFlow = calculator.calculateOperatingCashFlow(operatingLines);
        BigDecimal investingCashFlow = calculator.calculateInvestingCashFlow(investingLines);
        BigDecimal financingCashFlow = calculator.calculateFinancingCashFlow(financingLines);
        BigDecimal netCashFlow = calculator.calculateNetCashFlow(
                operatingCashFlow, investingCashFlow, financingCashFlow);

        BigDecimal openingCashBalance = resolveOpeningCashBalance(
                filter.getChartOfAccountsId(), fiscalPeriod);
        BigDecimal closingCashBalance = calculator.calculateClosingCashBalance(
                openingCashBalance, netCashFlow);

        CashFlowReport report = new CashFlowReport(
                filter.getChartOfAccountsId(),
                fiscalPeriod.getId(),
                fiscalPeriod.getName(),
                operatingLines,
                investingLines,
                financingLines,
                operatingCashFlow,
                investingCashFlow,
                financingCashFlow,
                netCashFlow,
                openingCashBalance,
                closingCashBalance);

        validator.validate(report);

        return report;
    }

    /**
     * Derives cashInflow and cashOutflow from ledger entries respecting account nature.
     *
     * REVENUE (credit-nature): credit = inflow, debit = outflow
     * EXPENSE (debit-nature):  debit  = outflow, credit = inflow
     * ASSET   (debit-nature):  debit  = outflow (acquisition), credit = inflow (disposal)
     * LIABILITY/EQUITY (credit-nature): credit = inflow (new debt/capital), debit = outflow (repayment)
     * OTHER: treated as OPERATING/REVENUE convention
     */
    private BigDecimal[] deriveInflowOutflow(Account account, List<LedgerEntry> entries) {
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        for (LedgerEntry entry : entries) {
            totalDebit = totalDebit.add(entry.getDebit() != null ? entry.getDebit() : BigDecimal.ZERO);
            totalCredit = totalCredit.add(entry.getCredit() != null ? entry.getCredit() : BigDecimal.ZERO);
        }

        AccountType type = account.getAccountType();

        // Credit-nature accounts: credit = inflow, debit = outflow
        if (AccountType.REVENUE.equals(type)
                || AccountType.LIABILITY.equals(type)
                || AccountType.EQUITY.equals(type)
                || AccountType.OTHER.equals(type)) {
            return new BigDecimal[]{totalCredit, totalDebit};
        }

        // Debit-nature accounts: debit = outflow, credit = inflow
        // ASSET, EXPENSE
        return new BigDecimal[]{totalCredit, totalDebit};
    }

    private BigDecimal resolveOpeningCashBalance(Long chartOfAccountsId, FiscalPeriod current) {
        Integer prevPeriodNumber = current.getPeriodNumber() - 1;
        Integer prevFiscalYear = current.getFiscalYear();

        if (prevPeriodNumber < 1) {
            prevPeriodNumber = 12;
            prevFiscalYear = current.getFiscalYear() - 1;
        }

        Optional<FiscalPeriod> previousPeriod = fiscalPeriodRepository
                .findByChartOfAccountsIdAndFiscalYearAndPeriodNumber(
                        chartOfAccountsId, prevFiscalYear, prevPeriodNumber);

        if (previousPeriod.isEmpty()) {
            return BigDecimal.ZERO;
        }

        List<LedgerEntry> prevEntries = ledgerEntryRepository
                .findByChartOfAccountsIdAndFiscalPeriodId(
                        chartOfAccountsId, previousPeriod.get().getId());

        // Opening cash = net cash generated in the previous period
        // Computed as sum of (credit - debit) for credit-nature accounts
        // plus sum of (credit - debit) for debit-nature accounts
        // which simplifies to: total credit - total debit across all entries
        BigDecimal prevCredit = BigDecimal.ZERO;
        BigDecimal prevDebit = BigDecimal.ZERO;
        for (LedgerEntry entry : prevEntries) {
            prevCredit = prevCredit.add(entry.getCredit() != null ? entry.getCredit() : BigDecimal.ZERO);
            prevDebit = prevDebit.add(entry.getDebit() != null ? entry.getDebit() : BigDecimal.ZERO);
        }

        return prevCredit.subtract(prevDebit);
    }

    private Map<Long, Account> buildAccountIndex(Long chartOfAccountsId, Boolean includeInactive) {
        List<Account> accounts = Boolean.TRUE.equals(includeInactive)
                ? accountRepository.findByChartOfAccountsId(chartOfAccountsId)
                : accountRepository.findByChartOfAccountsIdAndActive(chartOfAccountsId, Boolean.TRUE);

        Map<Long, Account> index = new LinkedHashMap<>();
        for (Account account : accounts) {
            index.put(account.getId(), account);
        }
        return index;
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

    private void validateFilter(CashFlowFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("CashFlowFilter cannot be null");
        }
        if (filter.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (filter.getFiscalPeriodId() == null) {
            throw new IllegalArgumentException("fiscalPeriodId cannot be null");
        }
    }
}
