package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;
import com.globaldynamicssystems.aurum.accounting.service.ProfitCenterValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultProfitCenterValidator implements ProfitCenterValidator {

    @Override
    public void validate(ProfitCenter profitCenter) {
        if (profitCenter == null) {
            throw new IllegalArgumentException("ProfitCenter cannot be null");
        }
        if (profitCenter.getCode() == null || profitCenter.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("ProfitCenter code is mandatory");
        }
        if (profitCenter.getName() == null || profitCenter.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("ProfitCenter name is mandatory");
        }
        if (profitCenter.getChartOfAccounts() == null) {
            throw new IllegalArgumentException("ProfitCenter chartOfAccounts is mandatory");
        }
        if (profitCenter.getStatus() == null) {
            throw new IllegalArgumentException("ProfitCenter status is mandatory");
        }
        if (profitCenter.getActive() == null) {
            throw new IllegalArgumentException("ProfitCenter active is mandatory");
        }
    }
}
