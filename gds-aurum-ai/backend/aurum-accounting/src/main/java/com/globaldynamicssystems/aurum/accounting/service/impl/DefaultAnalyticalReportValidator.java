package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultAnalyticalReportValidator implements AnalyticalReportValidator {

    @Override
    public void validate(AnalyticalReport report) {
        if (report == null) {
            throw new IllegalArgumentException("AnalyticalReport cannot be null");
        }
        if (report.getDimensionType() == null) {
            throw new IllegalArgumentException("AnalyticalReport dimensionType cannot be null");
        }
        if (report.getLines() == null) {
            throw new IllegalArgumentException("AnalyticalReport lines cannot be null");
        }
        if (report.getTotalDebit() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalDebit cannot be null");
        }
        if (report.getTotalCredit() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalCredit cannot be null");
        }
        if (report.getTotalBalance() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalBalance cannot be null");
        }
        if (report.getTotalRevenue() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalRevenue cannot be null");
        }
        if (report.getTotalExpense() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalExpense cannot be null");
        }
        if (report.getTotalNetResult() == null) {
            throw new IllegalArgumentException("AnalyticalReport totalNetResult cannot be null");
        }

        BigDecimal expectedBalance = report.getTotalDebit().subtract(report.getTotalCredit());
        if (report.getTotalBalance().compareTo(expectedBalance) != 0) {
            throw new IllegalArgumentException(
                    "AnalyticalReport totalBalance must equal totalDebit - totalCredit");
        }

        BigDecimal expectedNetResult = report.getTotalRevenue().subtract(report.getTotalExpense());
        if (report.getTotalNetResult().compareTo(expectedNetResult) != 0) {
            throw new IllegalArgumentException(
                    "AnalyticalReport totalNetResult must equal totalRevenue - totalExpense");
        }
    }
}
