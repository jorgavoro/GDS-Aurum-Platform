package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.CostCenter;

import java.util.List;
import java.util.Optional;

public interface CostCenterService {

    CostCenter create(Long chartOfAccountsId, CostCenter costCenter);

    Optional<CostCenter> findById(Long id);

    List<CostCenter> findByChartOfAccounts(Long chartOfAccountsId);

    void deactivate(Long id);
}