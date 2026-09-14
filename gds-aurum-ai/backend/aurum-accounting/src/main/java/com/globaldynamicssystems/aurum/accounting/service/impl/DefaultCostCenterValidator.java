package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.CostCenter;
import com.globaldynamicssystems.aurum.accounting.service.CostCenterValidator;
import org.springframework.stereotype.Service;

@Service
public class DefaultCostCenterValidator implements CostCenterValidator {

    @Override
    public void validate(CostCenter costCenter) {
        if (costCenter == null) {
            throw new IllegalArgumentException("CostCenter cannot be null.");
        }
        if (costCenter.getCode() == null || costCenter.getCode().isBlank()) {
            throw new IllegalArgumentException("CostCenter code is mandatory.");
        }
        if (costCenter.getName() == null || costCenter.getName().isBlank()) {
            throw new IllegalArgumentException("CostCenter name is mandatory.");
        }
        if (costCenter.getChartOfAccounts() == null) {
            throw new IllegalArgumentException("CostCenter ChartOfAccounts is mandatory.");
        }
        if (costCenter.getStatus() == null) {
            throw new IllegalArgumentException("CostCenter status is mandatory.");
        }
        if (costCenter.getActive() == null) {
            throw new IllegalArgumentException("CostCenter active flag is mandatory.");
        }
    }
}