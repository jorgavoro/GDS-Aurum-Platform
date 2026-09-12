package com.globaldynamicssystems.aurum.accounting.service;

import java.math.BigDecimal;

public interface FinancialRatioCalculator {

    BigDecimal calculateCurrentRatio(BigDecimal currentAssets, BigDecimal currentLiabilities);

    BigDecimal calculateQuickRatio(BigDecimal currentAssets, BigDecimal inventory,
                                   BigDecimal currentLiabilities);

    BigDecimal calculateDebtRatio(BigDecimal totalLiabilities, BigDecimal totalAssets);

    BigDecimal calculateDebtToEquity(BigDecimal totalLiabilities, BigDecimal totalEquity);

    BigDecimal calculateGrossMargin(BigDecimal revenue, BigDecimal costOfRevenue);

    BigDecimal calculateNetProfitMargin(BigDecimal netIncome, BigDecimal revenue);

    BigDecimal calculateReturnOnAssets(BigDecimal netIncome, BigDecimal totalAssets);

    BigDecimal calculateReturnOnEquity(BigDecimal netIncome, BigDecimal totalEquity);
}
