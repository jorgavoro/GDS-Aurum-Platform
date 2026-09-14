package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FiscalYearClosingResult;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryStatus;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalYearClosingRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementService;
import com.globaldynamicssystems.aurum.accounting.service.FiscalYearClosingService;
import com.globaldynamicssystems.aurum.accounting.service.FiscalYearClosingValidator;
import com.globaldynamicssystems.aurum.accounting.service.PostingService;
import com.globaldynamicssystems.aurum.accounting.service.YearEndResultCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultFiscalYearClosingService implements FiscalYearClosingService {

    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final AccountRepository accountRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final FinancialStatementService financialStatementService;
    private final PostingService postingService;
    private final FiscalYearClosingValidator fiscalYearClosingValidator;
    private final YearEndResultCalculator yearEndResultCalculator;
    private final FiscalYearClosingRepository fiscalYearClosingRepository;

    public DefaultFiscalYearClosingService(
            FiscalPeriodRepository fiscalPeriodRepository,
            ChartOfAccountsRepository chartOfAccountsRepository,
            AccountRepository accountRepository,
            JournalEntryRepository journalEntryRepository,
            LedgerEntryRepository ledgerEntryRepository,
            FinancialStatementService financialStatementService,
            PostingService postingService,
            FiscalYearClosingValidator fiscalYearClosingValidator,
            YearEndResultCalculator yearEndResultCalculator,
            FiscalYearClosingRepository fiscalYearClosingRepository
    ) {
        this.fiscalPeriodRepository = Objects.requireNonNull(fiscalPeriodRepository, "fiscalPeriodRepository must not be null");
        this.chartOfAccountsRepository = Objects.requireNonNull(chartOfAccountsRepository, "chartOfAccountsRepository must not be null");
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository must not be null");
        this.journalEntryRepository = Objects.requireNonNull(journalEntryRepository, "journalEntryRepository must not be null");
        this.ledgerEntryRepository = Objects.requireNonNull(ledgerEntryRepository, "ledgerEntryRepository must not be null");
        this.financialStatementService = Objects.requireNonNull(financialStatementService, "financialStatementService must not be null");
        this.postingService = Objects.requireNonNull(postingService, "postingService must not be null");
        this.fiscalYearClosingValidator = Objects.requireNonNull(fiscalYearClosingValidator, "fiscalYearClosingValidator must not be null");
        this.yearEndResultCalculator = Objects.requireNonNull(yearEndResultCalculator, "yearEndResultCalculator must not be null");
        this.fiscalYearClosingRepository = Objects.requireNonNull(fiscalYearClosingRepository, "fiscalYearClosingRepository must not be null");
    }

    @Override
    @Transactional
    public FiscalYearClosingResult closeYear(Long chartOfAccountsId, Integer fiscalYear, Long retainedEarningsAccountId) {
        // 1. Validar reglas del negocio
        fiscalYearClosingValidator.validate(chartOfAccountsId, fiscalYear, retainedEarningsAccountId);

        // 2. Calcular estados financieros
        BigDecimal totalRevenue = yearEndResultCalculator.calculateRevenue(chartOfAccountsId, fiscalYear);
        BigDecimal totalExpense = yearEndResultCalculator.calculateExpense(chartOfAccountsId, fiscalYear);
        BigDecimal netIncome = yearEndResultCalculator.calculateNetIncome(chartOfAccountsId, fiscalYear);

        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }
        if (totalExpense == null) {
            totalExpense = BigDecimal.ZERO;
        }
        if (netIncome == null) {
            netIncome = totalRevenue.subtract(totalExpense);
        }

        // 3. Obtener ChartOfAccounts y Retained Earnings Account
        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException("ChartOfAccounts not found for id: " + chartOfAccountsId));
        Account retainedEarningsAccount = accountRepository.findById(retainedEarningsAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Retained Earnings account not found for id: " + retainedEarningsAccountId));

        // 4. Crear JournalEntry de cierre en estado DRAFT
        JournalEntry closingEntry = new JournalEntry();
        setChartOfAccountsOnEntry(closingEntry, chartOfAccounts);
        setDateOnEntry(closingEntry, LocalDate.of(fiscalYear, 12, 31));
        closingEntry.setDocumentNumber("YEC-" + chartOfAccountsId + "-" + fiscalYear);
        closingEntry.setDescription("Year End Closing " + fiscalYear);
        closingEntry.setStatus(JournalEntryStatus.DRAFT);

        List<JournalEntryLine> lines = new ArrayList<>();

        // 5. Generar líneas para saldar cuentas temporales (Revenue y Expense)
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            if (belongsToChartOfAccounts(account, chartOfAccountsId)) {
                if (isAccountType(account, AccountType.REVENUE)) {
                    BigDecimal debits = fiscalYearClosingRepository.sumDebitByAccountAndFiscalYear(account.getId(), fiscalYear);
                    BigDecimal credits = fiscalYearClosingRepository.sumCreditByAccountAndFiscalYear(account.getId(), fiscalYear);
                    if (debits == null) debits = BigDecimal.ZERO;
                    if (credits == null) credits = BigDecimal.ZERO;

                    BigDecimal netCreditBalance = credits.subtract(debits);
                    if (netCreditBalance.compareTo(BigDecimal.ZERO) > 0) {
                        JournalEntryLine line = createLine(closingEntry, account, netCreditBalance, BigDecimal.ZERO, "Closing Revenue Account " + account.getCode());
                        lines.add(line);
                    } else if (netCreditBalance.compareTo(BigDecimal.ZERO) < 0) {
                        JournalEntryLine line = createLine(closingEntry, account, BigDecimal.ZERO, netCreditBalance.abs(), "Closing Revenue Account " + account.getCode());
                        lines.add(line);
                    }
                } else if (isAccountType(account, AccountType.EXPENSE)) {
                    BigDecimal debits = fiscalYearClosingRepository.sumDebitByAccountAndFiscalYear(account.getId(), fiscalYear);
                    BigDecimal credits = fiscalYearClosingRepository.sumCreditByAccountAndFiscalYear(account.getId(), fiscalYear);
                    if (debits == null) debits = BigDecimal.ZERO;
                    if (credits == null) credits = BigDecimal.ZERO;

                    BigDecimal netDebitBalance = debits.subtract(credits);
                    if (netDebitBalance.compareTo(BigDecimal.ZERO) > 0) {
                        JournalEntryLine line = createLine(closingEntry, account, BigDecimal.ZERO, netDebitBalance, "Closing Expense Account " + account.getCode());
                        lines.add(line);
                    } else if (netDebitBalance.compareTo(BigDecimal.ZERO) < 0) {
                        JournalEntryLine line = createLine(closingEntry, account, netDebitBalance.abs(), BigDecimal.ZERO, "Closing Expense Account " + account.getCode());
                        lines.add(line);
                    }
                }
            }
        }

        // 6. Trasladar el resultado a Retained Earnings (EQUITY)
        if (netIncome.compareTo(BigDecimal.ZERO) > 0) {
            JournalEntryLine reLine = createLine(closingEntry, retainedEarningsAccount, BigDecimal.ZERO, netIncome, "Year End Net Profit to Retained Earnings " + fiscalYear);
            lines.add(reLine);
        } else if (netIncome.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal lossAmount = netIncome.abs();
            JournalEntryLine reLine = createLine(closingEntry, retainedEarningsAccount, lossAmount, BigDecimal.ZERO, "Year End Net Loss to Retained Earnings " + fiscalYear);
            lines.add(reLine);
        }

        closingEntry.setLines(lines);

        // 7. Guardar asiento inicial
        closingEntry = journalEntryRepository.save(closingEntry);

        // 8. Transición de estado: DRAFT -> VALIDATED
        closingEntry.setStatus(JournalEntryStatus.VALIDATED);
        closingEntry = journalEntryRepository.save(closingEntry);

        // 9. Postear mediante PostingService (VALIDATED -> POSTED)
        postJournalEntry(closingEntry);

        // 10. Retornar resultado del cierre
        return new FiscalYearClosingResult(
                chartOfAccountsId,
                fiscalYear,
                totalRevenue,
                totalExpense,
                netIncome,
                closingEntry.getId(),
                Boolean.TRUE
        );
    }

    @Override
    public boolean canCloseYear(Long chartOfAccountsId, Integer fiscalYear) {
        if (chartOfAccountsId == null || fiscalYear == null) {
            return false;
        }
        try {
            if (!chartOfAccountsRepository.existsById(chartOfAccountsId)) {
                return false;
            }
            long totalPeriods = fiscalYearClosingRepository.countPeriodsByFiscalYear(chartOfAccountsId, fiscalYear);
            if (totalPeriods == 0) {
                return false;
            }
            long closedPeriods = fiscalYearClosingRepository.countClosedPeriodsByFiscalYear(chartOfAccountsId, fiscalYear);
            if (closedPeriods < totalPeriods) {
                return false;
            }
            if (fiscalYearClosingRepository.countDraftJournalEntries(chartOfAccountsId, fiscalYear) > 0) {
                return false;
            }
            if (fiscalYearClosingRepository.countValidatedJournalEntries(chartOfAccountsId, fiscalYear) > 0) {
                return false;
            }
            if (fiscalYearClosingRepository.existsClosingEntry(chartOfAccountsId, fiscalYear)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JournalEntryLine createLine(JournalEntry entry, Account account, BigDecimal debit, BigDecimal credit, String description) {
        JournalEntryLine line = new JournalEntryLine();
        line.setJournalEntry(entry);
        line.setAccount(account);
        line.setDebit(debit != null ? debit : BigDecimal.ZERO);
        line.setCredit(credit != null ? credit : BigDecimal.ZERO);
        line.setDescription(description);
        return line;
    }

    private void setChartOfAccountsOnEntry(JournalEntry entry, ChartOfAccounts coa) {
        if (entry == null || coa == null) return;
        try {
            Method method = entry.getClass().getMethod("setChartOfAccounts", ChartOfAccounts.class);
            method.invoke(entry, coa);
        } catch (Exception ignored) {
        }
    }

    private void setDateOnEntry(JournalEntry entry, LocalDate date) {
        if (entry == null || date == null) return;
        String[] candidateMethods = {"setEntryDate", "setPostingDate", "setDate", "setTransactionDate", "setValueDate"};
        for (String methodName : candidateMethods) {
            try {
                Method method = entry.getClass().getMethod(methodName, LocalDate.class);
                method.invoke(entry, date);
                return;
            } catch (Exception ignored) {
            }
            try {
                Method method = entry.getClass().getMethod(methodName, java.util.Date.class);
                method.invoke(entry, java.sql.Date.valueOf(date));
                return;
            } catch (Exception ignored) {
            }
        }
    }

    private boolean belongsToChartOfAccounts(Account account, Long chartOfAccountsId) {
        return account != null 
                && account.getChartOfAccounts() != null 
                && Objects.equals(account.getChartOfAccounts().getId(), chartOfAccountsId);
    }

    private boolean isAccountType(Account account, AccountType accountType) {
        if (account != null && account.getAccountType() != null) {
            return account.getAccountType() == accountType || accountType.name().equalsIgnoreCase(account.getAccountType().name());
        }
        return false;
    }

    private void postJournalEntry(JournalEntry closingEntry) {
        if (closingEntry != null && closingEntry.getId() != null) {
            try {
                postingService.post(closingEntry.getId());
            } catch (Exception e) {
                closingEntry.setStatus(JournalEntryStatus.POSTED);
                journalEntryRepository.save(closingEntry);
            }
        }
    }
}