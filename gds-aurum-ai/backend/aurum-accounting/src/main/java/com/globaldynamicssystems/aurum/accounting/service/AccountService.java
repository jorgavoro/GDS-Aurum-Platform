package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account create(Long chartOfAccountsId, Account account);

    Optional<Account> findByCode(Long chartOfAccountsId, String code);

    List<Account> findByChartOfAccounts(Long chartOfAccountsId);

    List<Account> findChildren(Long parentId);
}