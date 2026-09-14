package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialRatio;
import com.globaldynamicssystems.aurum.accounting.model.FinancialRatioReport;
import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultFinancialRatioValidator implements FinancialRatioValidator {

    @Override
    public void validate(FinancialRatioReport report) {
        if (report == null) {
            throw new IllegalStateException("FinancialRatioReport cannot be null");
        }
        if (report.getChartOfAccountsId() == null) {
            throw new IllegalStateException("chartOfAccountsId cannot be null");
        }
        if (report.getFiscalPeriodId() == null) {
            throw new IllegalStateException("fiscalPeriodId cannot be null");
        }
        if (report.getRatios() == null) {
            throw new IllegalStateException("ratios cannot be null");
        }
        for (FinancialRatio ratio : report.getRatios()) {
            if (ratio.getType() == null) {
                throw new IllegalStateException("FinancialRatio.type cannot be null");
            }
            if (ratio.getCode() == null || ratio.getCode().isBlank()) {
                throw new IllegalStateException("FinancialRatio.code cannot be null or blank");
            }
            if (ratio.getName() == null || ratio.getName().isBlank()) {
                throw new IllegalStateException("FinancialRatio.name cannot be null or blank");
            }
            if (ratio.getValue() == null) {
                throw new IllegalStateException("FinancialRatio.value cannot be null");
            }
            if (ratio.getUnit() == null || ratio.getUnit().isBlank()) {
                throw new IllegalStateException("FinancialRatio.unit cannot be null or blank");
            }
        }
    }
}
