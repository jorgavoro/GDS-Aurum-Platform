package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.OpeningBalanceException;
import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceLine;
import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceResult;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.FiscalPeriodRepository;
import com.globaldynamicssystems.aurum.accounting.repository.JournalEntryRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.FinancialStatementService;
import com.globaldynamicssystems.aurum.accounting.service.JournalEntryValidator;
import com.globaldynamicssystems.aurum.accounting.service.OpeningBalanceCalculator;
import com.globaldynamicssystems.aurum.accounting.service.OpeningBalanceService;
import com.globaldynamicssystems.aurum.accounting.service.OpeningBalanceValidator;
import com.globaldynamicssystems.aurum.accounting.service.PostingService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultOpeningBalanceService implements OpeningBalanceService {

    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final FiscalPeriodRepository fiscalPeriodRepository;
    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final FinancialStatementService financialStatementService;
    private final JournalEntryValidator journalEntryValidator;
    private final PostingService postingService;
    private final OpeningBalanceValidator openingBalanceValidator;
    private final OpeningBalanceCalculator openingBalanceCalculator;

    public DefaultOpeningBalanceService(
            ChartOfAccountsRepository chartOfAccountsRepository,
            FiscalPeriodRepository fiscalPeriodRepository,
            AccountRepository accountRepository,
            LedgerEntryRepository ledgerEntryRepository,
            JournalEntryRepository journalEntryRepository,
            FinancialStatementService financialStatementService,
            JournalEntryValidator journalEntryValidator,
            PostingService postingService,
            OpeningBalanceValidator openingBalanceValidator,
            OpeningBalanceCalculator openingBalanceCalculator) {
        this.chartOfAccountsRepository = Objects.requireNonNull(chartOfAccountsRepository, "chartOfAccountsRepository must not be null");
        this.fiscalPeriodRepository = Objects.requireNonNull(fiscalPeriodRepository, "fiscalPeriodRepository must not be null");
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository must not be null");
        this.ledgerEntryRepository = Objects.requireNonNull(ledgerEntryRepository, "ledgerEntryRepository must not be null");
        this.journalEntryRepository = Objects.requireNonNull(journalEntryRepository, "journalEntryRepository must not be null");
        this.financialStatementService = Objects.requireNonNull(financialStatementService, "financialStatementService must not be null");
        this.journalEntryValidator = Objects.requireNonNull(journalEntryValidator, "journalEntryValidator must not be null");
        this.postingService = Objects.requireNonNull(postingService, "postingService must not be null");
        this.openingBalanceValidator = Objects.requireNonNull(openingBalanceValidator, "openingBalanceValidator must not be null");
        this.openingBalanceCalculator = Objects.requireNonNull(openingBalanceCalculator, "openingBalanceCalculator must not be null");
    }

    @Override
    public OpeningBalanceResult generate(Long chartOfAccountsId, Integer sourceFiscalYear, Integer targetFiscalYear) {
        validateGenerateParams(chartOfAccountsId, sourceFiscalYear, targetFiscalYear);

        List<OpeningBalanceLine> lines = openingBalanceCalculator.calculate(chartOfAccountsId, sourceFiscalYear);
        BigDecimal totalDebit = openingBalanceCalculator.calculateDebitTotal(lines);
        BigDecimal totalCredit = openingBalanceCalculator.calculateCreditTotal(lines);

        boolean balanced = totalDebit.compareTo(totalCredit) == 0;

        OpeningBalanceResult result = new OpeningBalanceResult(
                chartOfAccountsId,
                sourceFiscalYear,
                targetFiscalYear,
                null,
                lines,
                totalDebit,
                totalCredit,
                balanced,
                balanced
        );

        openingBalanceValidator.validate(result);

        return result;
    }

    @Override
    @Transactional
    public OpeningBalanceResult post(Long chartOfAccountsId, Integer sourceFiscalYear, Integer targetFiscalYear) {
        OpeningBalanceResult result = generate(chartOfAccountsId, sourceFiscalYear, targetFiscalYear);

        if (!Boolean.TRUE.equals(result.getBalanced()) || !Boolean.TRUE.equals(result.getSuccessful())) {
            throw new OpeningBalanceException("Cannot post unbalanced opening balance result.");
        }

        ChartOfAccounts coa = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new OpeningBalanceException("ChartOfAccounts not found: " + chartOfAccountsId));

        List<FiscalPeriod> targetPeriods = findFiscalPeriods(chartOfAccountsId, targetFiscalYear);
        FiscalPeriod firstPeriod = targetPeriods.stream()
                .filter(fp -> fp.getStartDate() != null)
                .min(Comparator.comparing(FiscalPeriod::getStartDate))
                .orElseThrow(() -> new OpeningBalanceException("Target fiscal year periods not found."));

        String documentNumber = "OPENING-" + targetFiscalYear;

        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDocumentNumber(documentNumber);
        journalEntry.setDescription("Opening Balance Journal Entry " + targetFiscalYear);
        journalEntry.setChartOfAccounts(coa);
        journalEntry.setFiscalPeriod(firstPeriod);
        journalEntry.setAccountingDate(firstPeriod.getStartDate());

        List<JournalEntryLine> entryLines = new ArrayList<>();
        for (OpeningBalanceLine line : result.getLines()) {
            Account account = accountRepository.findById(line.getAccountId())
                    .orElseThrow(() -> new OpeningBalanceException("Account not found: " + line.getAccountId()));

            JournalEntryLine journalLine = new JournalEntryLine();
            journalLine.setJournalEntry(journalEntry);
            journalLine.setAccount(account);
            journalLine.setDebit(line.getDebit());
            journalLine.setCredit(line.getCredit());
            journalLine.setDescription("Opening Balance for " + account.getCode());
            entryLines.add(journalLine);
        }

        journalEntry.setLines(entryLines);

        journalEntryValidator.validate(journalEntry);

        // Guardar primero para obtener el ID y pasar Long a postingService.post(...)
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        postingService.post(savedEntry.getId());

        result.setOpeningJournalEntryId(savedEntry.getId());
        result.setSuccessful(true);

        return result;
    }

    @Override
    public boolean exists(Long chartOfAccountsId, Integer targetFiscalYear) {
        if (chartOfAccountsId == null || targetFiscalYear == null) {
            return false;
        }
        String documentNumber = "OPENING-" + targetFiscalYear;
        return journalEntryRepository.findAll().stream()
                .anyMatch(je -> documentNumber.equals(je.getDocumentNumber()) &&
                        je.getChartOfAccounts() != null &&
                        Objects.equals(je.getChartOfAccounts().getId(), chartOfAccountsId));
    }

    private List<FiscalPeriod> findFiscalPeriods(Long chartOfAccountsId, Integer fiscalYear) {
        return fiscalPeriodRepository.findAll().stream()
                .filter(fp -> fp.getChartOfAccounts() != null &&
                        Objects.equals(fp.getChartOfAccounts().getId(), chartOfAccountsId) &&
                        Objects.equals(fp.getFiscalYear(), fiscalYear))
                .collect(Collectors.toList());
    }

    private boolean isPeriodClosed(FiscalPeriod period) {
        if (period == null || period.getStatus() == null) {
            return false;
        }
        return "CLOSED".equalsIgnoreCase(period.getStatus().toString());
    }

    private boolean isPeriodOpen(FiscalPeriod period) {
        if (period == null || period.getStatus() == null) {
            return false;
        }
        return "OPEN".equalsIgnoreCase(period.getStatus().toString());
    }

    private void validateGenerateParams(Long chartOfAccountsId, Integer sourceFiscalYear, Integer targetFiscalYear) {
        if (chartOfAccountsId == null) {
            throw new OpeningBalanceException("chartOfAccountsId is mandatory.");
        }
        if (sourceFiscalYear == null) {
            throw new OpeningBalanceException("sourceFiscalYear is mandatory.");
        }
        if (targetFiscalYear == null) {
            throw new OpeningBalanceException("targetFiscalYear is mandatory.");
        }
        if (sourceFiscalYear >= targetFiscalYear) {
            throw new OpeningBalanceException("sourceFiscalYear must be strictly less than targetFiscalYear.");
        }
        if (!chartOfAccountsRepository.existsById(chartOfAccountsId)) {
            throw new OpeningBalanceException("ChartOfAccounts does not exist with ID: " + chartOfAccountsId);
        }

        List<FiscalPeriod> sourcePeriods = findFiscalPeriods(chartOfAccountsId, sourceFiscalYear);
        if (sourcePeriods == null || sourcePeriods.isEmpty()) {
            throw new OpeningBalanceException("No fiscal periods found for source fiscal year: " + sourceFiscalYear);
        }

        boolean allSourceClosed = sourcePeriods.stream().allMatch(this::isPeriodClosed);
        if (!allSourceClosed) {
            throw new OpeningBalanceException("All periods of source fiscal year " + sourceFiscalYear + " must be CLOSED.");
        }

        List<FiscalPeriod> targetPeriods = findFiscalPeriods(chartOfAccountsId, targetFiscalYear);
        if (targetPeriods == null || targetPeriods.isEmpty()) {
            throw new OpeningBalanceException("No fiscal periods found for target fiscal year: " + targetFiscalYear);
        }

        FiscalPeriod firstTargetPeriod = targetPeriods.stream()
                .filter(fp -> fp.getStartDate() != null)
                .min(Comparator.comparing(FiscalPeriod::getStartDate))
                .orElseThrow(() -> new OpeningBalanceException("Target fiscal year periods invalid."));

        if (!isPeriodOpen(firstTargetPeriod)) {
            throw new OpeningBalanceException("Opening Balance REJECTED: First target period must be OPEN.");
        }

        if (exists(chartOfAccountsId, targetFiscalYear)) {
            throw new OpeningBalanceException("An Opening Balance entry already exists for target fiscal year " + targetFiscalYear);
        }
    }
}