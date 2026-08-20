package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.CostCenter;
import com.globaldynamicssystems.aurum.accounting.model.CostCenterStatus;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.CostCenterRepository;
import com.globaldynamicssystems.aurum.accounting.service.CostCenterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCostCenterService implements CostCenterService {

    private final CostCenterRepository costCenterRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public DefaultCostCenterService(CostCenterRepository costCenterRepository,
                                   ChartOfAccountsRepository chartOfAccountsRepository) {
        this.costCenterRepository = costCenterRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @Override
    public CostCenter create(Long chartOfAccountsId, CostCenter costCenter) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("Chart of accounts ID is mandatory.");
        }
        if (costCenter == null) {
            throw new IllegalArgumentException("Cost center cannot be null.");
        }
        if (costCenter.getCode() == null || costCenter.getCode().isBlank()) {
            throw new IllegalArgumentException("Cost center code is mandatory.");
        }
        if (costCenter.getName() == null || costCenter.getName().isBlank()) {
            throw new IllegalArgumentException("Cost center name is mandatory.");
        }

        ChartOfAccounts coa = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException("Chart of accounts not found with ID: " + chartOfAccountsId));

        if (costCenterRepository.existsByChartOfAccountsIdAndCode(chartOfAccountsId, costCenter.getCode())) {
            throw new IllegalArgumentException(
                    "Cost center code '" + costCenter.getCode() + "' already exists in Chart of Accounts " + chartOfAccountsId
            );
        }

        costCenter.setChartOfAccounts(coa);
        costCenter.setStatus(CostCenterStatus.ACTIVE);
        costCenter.setActive(Boolean.TRUE);

        return costCenterRepository.save(costCenter);
    }

    @Override
    public Optional<CostCenter> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return costCenterRepository.findById(id);
    }

    @Override
    public List<CostCenter> findByChartOfAccounts(Long chartOfAccountsId) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("Chart of accounts ID cannot be null.");
        }
        return costCenterRepository.findByChartOfAccountsId(chartOfAccountsId);
    }

    @Override
    public void deactivate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cost center ID cannot be null.");
        }

        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cost center not found with ID: " + id));

        costCenter.setStatus(CostCenterStatus.INACTIVE);
        costCenter.setActive(Boolean.FALSE);

        costCenterRepository.save(costCenter);
    }
}