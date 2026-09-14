package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;

import java.util.List;
import java.util.Optional;

public interface ProfitCenterService {

    ProfitCenter create(Long chartOfAccountsId, ProfitCenter profitCenter);

    Optional<ProfitCenter> findById(Long id);

    List<ProfitCenter> findByChartOfAccounts(Long chartOfAccountsId);

    void deactivate(Long id);
}
