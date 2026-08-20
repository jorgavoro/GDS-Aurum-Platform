package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.LedgerEntry;
import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceLine;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.LedgerEntryRepository;
import com.globaldynamicssystems.aurum.accounting.service.OpeningBalanceCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultOpeningBalanceCalculator implements OpeningBalanceCalculator {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public DefaultOpeningBalanceCalculator(LedgerEntryRepository ledgerEntryRepository, AccountRepository accountRepository) {
        this.ledgerEntryRepository = Objects.requireNonNull(ledgerEntryRepository, "ledgerEntryRepository must not be null");
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository must not be null");
    }

    @Override
    public List<OpeningBalanceLine> calculate(Long chartOfAccountsId, Integer sourceFiscalYear) {
        List<Account> accounts = accountRepository.findAll().stream()
                .filter(acc -> acc.getChartOfAccounts() != null && Objects.equals(acc.getChartOfAccounts().getId(), chartOfAccountsId))
                .collect(Collectors.toList());

        List<LedgerEntry> allLedgerEntries = ledgerEntryRepository.findAll();
        List<OpeningBalanceLine> lines = new ArrayList<>();

        for (Account account : accounts) {
            AccountType type = account.getAccountType();

            if (type == AccountType.REVENUE || type == AccountType.EXPENSE) {
                continue;
            }

            if (type == AccountType.ASSET || type == AccountType.LIABILITY || type == AccountType.EQUITY) {
                BigDecimal endingBalance = allLedgerEntries.stream()
                        .filter(le -> le.getAccount() != null && Objects.equals(le.getAccount().getId(), account.getId()) &&
                                le.getFiscalPeriod() != null && Objects.equals(le.getFiscalPeriod().getFiscalYear(), sourceFiscalYear))
                        .map(le -> {
                            BigDecimal d = le.getDebit() != null ? le.getDebit() : BigDecimal.ZERO;
                            BigDecimal c = le.getCredit() != null ? le.getCredit() : BigDecimal.ZERO;
                            return d.subtract(c);
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (endingBalance.compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }

                BigDecimal debit;
                BigDecimal credit;

                if (endingBalance.compareTo(BigDecimal.ZERO) > 0) {
                    debit = endingBalance;
                    credit = BigDecimal.ZERO;
                } else {
                    debit = BigDecimal.ZERO;
                    credit = endingBalance.abs();
                }

                BigDecimal netBalance = debit.subtract(credit);

                OpeningBalanceLine line = new OpeningBalanceLine(
                        account.getId(),
                        account.getCode(),
                        account.getName(),
                        debit,
                        credit,
                        netBalance
                );

                lines.add(line);
            }
        }

        return lines;
    }

    @Override
    public BigDecimal calculateDebitTotal(List<OpeningBalanceLine> lines) {
        if (lines == null || lines.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return lines.stream()
                .map(OpeningBalanceLine::getDebit)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCreditTotal(List<OpeningBalanceLine> lines) {
        if (lines == null || lines.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return lines.stream()
                .map(OpeningBalanceLine::getCredit)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}