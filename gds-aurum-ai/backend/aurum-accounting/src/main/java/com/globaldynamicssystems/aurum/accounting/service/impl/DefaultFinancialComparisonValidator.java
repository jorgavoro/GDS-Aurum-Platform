package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialComparisonReport;
import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultFinancialComparisonValidator implements FinancialComparisonValidator {

    @Override
    public void validate(FinancialComparisonReport report) {
        if (report == null) {
            throw new IllegalArgumentException("FinancialComparisonReport cannot be null");
        }
        if (report.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport chartOfAccountsId cannot be null");
        }
        if (report.getCurrentFiscalPeriodId() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport currentFiscalPeriodId cannot be null");
        }
        if (report.getPreviousFiscalPeriodId() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport previousFiscalPeriodId cannot be null");
        }
        if (report.getCurrentFiscalPeriodId().equals(report.getPreviousFiscalPeriodId())) {
            throw new IllegalArgumentException(
                    "currentFiscalPeriodId and previousFiscalPeriodId must be different");
        }
        if (report.getDimensionType() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport dimensionType cannot be null");
        }
        if (report.getLines() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport lines cannot be null");
        }
        if (report.getTotalCurrentRevenue() == null || report.getTotalPreviousRevenue() == null
                || report.getTotalRevenueVariation() == null
                || report.getTotalCurrentExpense() == null || report.getTotalPreviousExpense() == null
                || report.getTotalExpenseVariation() == null
                || report.getTotalCurrentNetResult() == null || report.getTotalPreviousNetResult() == null
                || report.getTotalNetResultVariation() == null
                || report.getTotalCurrentMarginPercentage() == null
                || report.getTotalPreviousMarginPercentage() == null
                || report.getTotalMarginPercentageVariation() == null) {
            throw new IllegalArgumentException("FinancialComparisonReport totals cannot be null");
        }
    }
}
