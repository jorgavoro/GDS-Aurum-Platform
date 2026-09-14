package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowLine;

import java.math.BigDecimal;
import java.util.List;

public interface CashFlowCalculator {

    BigDecimal calculateOperatingCashFlow(List<CashFlowLine> lines);

    BigDecimal calculateInvestingCashFlow(List<CashFlowLine> lines);

    BigDecimal calculateFinancingCashFlow(List<CashFlowLine> lines);

    BigDecimal calculateNetCashFlow(BigDecimal operatingCashFlow,
                                    BigDecimal investingCashFlow,
                                    BigDecimal financingCashFlow);

    BigDecimal calculateClosingCashBalance(BigDecimal openingCashBalance, BigDecimal netCashFlow);
}
