package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultCashFlowValidator implements CashFlowValidator {

    @Override
    public void validate(CashFlowReport report) {
        if (report == null) {
            throw new IllegalStateException("CashFlowReport cannot be null");
        }
        if (report.getChartOfAccountsId() == null) {
            throw new IllegalStateException("chartOfAccountsId cannot be null");
        }
        if (report.getFiscalPeriodId() == null) {
            throw new IllegalStateException("fiscalPeriodId cannot be null");
        }
        if (report.getOperatingLines() == null) {
            throw new IllegalStateException("operatingLines cannot be null");
        }
        if (report.getInvestingLines() == null) {
            throw new IllegalStateException("investingLines cannot be null");
        }
        if (report.getFinancingLines() == null) {
            throw new IllegalStateException("financingLines cannot be null");
        }
        if (report.getOperatingCashFlow() == null) {
            throw new IllegalStateException("operatingCashFlow cannot be null");
        }
        if (report.getInvestingCashFlow() == null) {
            throw new IllegalStateException("investingCashFlow cannot be null");
        }
        if (report.getFinancingCashFlow() == null) {
            throw new IllegalStateException("financingCashFlow cannot be null");
        }
        if (report.getNetCashFlow() == null) {
            throw new IllegalStateException("netCashFlow cannot be null");
        }
        if (report.getOpeningCashBalance() == null) {
            throw new IllegalStateException("openingCashBalance cannot be null");
        }
        if (report.getClosingCashBalance() == null) {
            throw new IllegalStateException("closingCashBalance cannot be null");
        }

        BigDecimal expectedNet = report.getOperatingCashFlow()
                .add(report.getInvestingCashFlow())
                .add(report.getFinancingCashFlow());
        if (expectedNet.compareTo(report.getNetCashFlow()) != 0) {
            throw new IllegalStateException(
                    "netCashFlow must equal operatingCashFlow + investingCashFlow + financingCashFlow");
        }

        BigDecimal expectedClosing = report.getOpeningCashBalance().add(report.getNetCashFlow());
        if (expectedClosing.compareTo(report.getClosingCashBalance()) != 0) {
            throw new IllegalStateException(
                    "closingCashBalance must equal openingCashBalance + netCashFlow");
        }
    }
}
