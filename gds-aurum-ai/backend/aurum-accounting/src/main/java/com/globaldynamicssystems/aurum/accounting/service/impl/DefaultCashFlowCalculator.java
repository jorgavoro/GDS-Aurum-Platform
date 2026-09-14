package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowLine;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DefaultCashFlowCalculator implements CashFlowCalculator {

    @Override
    public BigDecimal calculateOperatingCashFlow(List<CashFlowLine> lines) {
        return sumNetCashFlow(lines);
    }

    @Override
    public BigDecimal calculateInvestingCashFlow(List<CashFlowLine> lines) {
        return sumNetCashFlow(lines);
    }

    @Override
    public BigDecimal calculateFinancingCashFlow(List<CashFlowLine> lines) {
        return sumNetCashFlow(lines);
    }

    @Override
    public BigDecimal calculateNetCashFlow(BigDecimal operatingCashFlow,
                                           BigDecimal investingCashFlow,
                                           BigDecimal financingCashFlow) {
        return safe(operatingCashFlow)
                .add(safe(investingCashFlow))
                .add(safe(financingCashFlow));
    }

    @Override
    public BigDecimal calculateClosingCashBalance(BigDecimal openingCashBalance,
                                                  BigDecimal netCashFlow) {
        return safe(openingCashBalance).add(safe(netCashFlow));
    }

    private BigDecimal sumNetCashFlow(List<CashFlowLine> lines) {
        if (lines == null) {
            return BigDecimal.ZERO;
        }
        return lines.stream()
                .map(l -> l.getNetCashFlow() != null ? l.getNetCashFlow() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
