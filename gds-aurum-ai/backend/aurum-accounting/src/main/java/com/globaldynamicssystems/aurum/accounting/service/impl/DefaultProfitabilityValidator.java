package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityCalculator;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultProfitabilityValidator implements ProfitabilityValidator {

    private final ProfitabilityCalculator calculator;

    public DefaultProfitabilityValidator(ProfitabilityCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void validate(ProfitabilityReport report) {
        if (report == null) {
            throw new IllegalArgumentException("ProfitabilityReport cannot be null");
        }
        if (report.getDimensionType() == null) {
            throw new IllegalArgumentException("ProfitabilityReport dimensionType cannot be null");
        }
        if (report.getLines() == null) {
            throw new IllegalArgumentException("ProfitabilityReport lines cannot be null");
        }
        if (report.getTotalRevenue() == null) {
            throw new IllegalArgumentException("ProfitabilityReport totalRevenue cannot be null");
        }
        if (report.getTotalExpense() == null) {
            throw new IllegalArgumentException("ProfitabilityReport totalExpense cannot be null");
        }
        if (report.getTotalNetResult() == null) {
            throw new IllegalArgumentException("ProfitabilityReport totalNetResult cannot be null");
        }
        if (report.getTotalMargin() == null) {
            throw new IllegalArgumentException("ProfitabilityReport totalMargin cannot be null");
        }
        if (report.getTotalMarginPercentage() == null) {
            throw new IllegalArgumentException("ProfitabilityReport totalMarginPercentage cannot be null");
        }

        BigDecimal expectedNetResult = report.getTotalRevenue().subtract(report.getTotalExpense());
        if (report.getTotalNetResult().compareTo(expectedNetResult) != 0) {
            throw new IllegalArgumentException(
                    "ProfitabilityReport totalNetResult must equal totalRevenue - totalExpense");
        }

        if (report.getTotalMargin().compareTo(report.getTotalNetResult()) != 0) {
            throw new IllegalArgumentException(
                    "ProfitabilityReport totalMargin must equal totalNetResult");
        }

        BigDecimal expectedMarginPct = calculator.calculateMarginPercentage(
                report.getTotalRevenue(), report.getTotalNetResult());

        if (report.getTotalMarginPercentage().compareTo(expectedMarginPct) != 0) {
            throw new IllegalArgumentException(
                    "ProfitabilityReport totalMarginPercentage does not match the expected calculation");
        }
    }
}
