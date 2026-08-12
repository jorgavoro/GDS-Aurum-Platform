package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;

import java.util.List;
import java.util.Optional;

public interface ChartOfAccountsService {

    ChartOfAccounts create(ChartOfAccounts chartOfAccounts);

    Optional<ChartOfAccounts> findByCode(String code);

    List<ChartOfAccounts> findAll();
}