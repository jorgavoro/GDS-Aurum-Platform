package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.repository.AccountRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultAccountService implements AccountService {

    private final AccountRepository accountRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public DefaultAccountService(AccountRepository accountRepository,
                                 ChartOfAccountsRepository chartOfAccountsRepository) {
        this.accountRepository = accountRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @Override
    public Account create(Long chartOfAccountsId, Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account instance cannot be null");
        }
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("ChartOfAccounts ID cannot be null");
        }

        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException("ChartOfAccounts not found with ID: " + chartOfAccountsId));

        if (account.getCode() == null || account.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Account code cannot be null or empty");
        }
        if (account.getName() == null || account.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be null or empty");
        }
        if (account.getAccountType() == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }
        if (account.getNature() == null) {
            throw new IllegalArgumentException("Account nature cannot be null");
        }
        if (account.getActive() == null) {
            throw new IllegalArgumentException("Account active status cannot be null");
        }
        if (account.getPostable() == null) {
            throw new IllegalArgumentException("Account postable status cannot be null");
        }
        if (account.getLevel() == null) {
            throw new IllegalArgumentException("Account level cannot be null");
        }

        if (accountRepository.existsByChartOfAccountsIdAndCode(chartOfAccountsId, account.getCode())) {
            throw new IllegalArgumentException("Account with code '" + account.getCode() 
                    + "' already exists in ChartOfAccounts ID: " + chartOfAccountsId);
        }

        account.setChartOfAccounts(chartOfAccounts);

        if (account.getParent() != null) {
            Long parentId = account.getParent().getId();
            if (parentId == null) {
                throw new IllegalArgumentException("Parent account ID cannot be null when parent is specified");
            }

            Account parent = accountRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent account not found with ID: " + parentId));

            if (parent.getChartOfAccounts() == null || !chartOfAccountsId.equals(parent.getChartOfAccounts().getId())) {
                throw new IllegalArgumentException("Child account cannot belong to a parent from a different ChartOfAccounts");
            }

            if (!Integer.valueOf(parent.getLevel() + 1).equals(account.getLevel())) {
                throw new IllegalArgumentException("Child account level (" + account.getLevel() 
                        + ") must be parent level + 1 (" + (parent.getLevel() + 1) + ")");
            }

            account.setParent(parent);
        }

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByCode(Long chartOfAccountsId, String code) {
        if (chartOfAccountsId == null || code == null || code.trim().isEmpty()) {
            return Optional.empty();
        }
        return accountRepository.findByChartOfAccountsIdAndCode(chartOfAccountsId, code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByChartOfAccounts(Long chartOfAccountsId) {
        if (chartOfAccountsId == null) {
            return List.of();
        }
        return accountRepository.findByChartOfAccountsId(chartOfAccountsId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findChildren(Long parentId) {
        if (parentId == null) {
            return List.of();
        }
        return accountRepository.findByParentId(parentId);
    }
}