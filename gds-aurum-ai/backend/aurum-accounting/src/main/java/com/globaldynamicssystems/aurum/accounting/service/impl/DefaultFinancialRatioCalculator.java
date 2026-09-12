package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.FinancialRatioCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DefaultFinancialRatioCalculator implements FinancialRatioCalculator {

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    @Override
    public BigDecimal calculateCurrentRatio(BigDecimal currentAssets, BigDecimal currentLiabilities) {
        return safeDivide(safe(currentAssets), safe(currentLiabilities));
    }

    @Override
    public BigDecimal calculateQuickRatio(BigDecimal currentAssets, BigDecimal inventory,
                                          BigDecimal currentLiabilities) {
        BigDecimal liquid = safe(currentAssets).subtract(safe(inventory));
        return safeDivide(liquid, safe(currentLiabilities));
    }

    @Override
    public BigDecimal calculateDebtRatio(BigDecimal totalLiabilities, BigDecimal totalAssets) {
        return safeDivide(safe(totalLiabilities), safe(totalAssets));
    }

    @Override
    public BigDecimal calculateDebtToEquity(BigDecimal totalLiabilities, BigDecimal totalEquity) {
        return safeDivide(safe(totalLiabilities), safe(totalEquity));
    }

    @Override
    public BigDecimal calculateGrossMargin(BigDecimal revenue, BigDecimal costOfRevenue) {
        BigDecimal grossProfit = safe(revenue).subtract(safe(costOfRevenue));
        return safeDivide(grossProfit, safe(revenue)).multiply(HUNDRED)
                .setScale(SCALE, ROUNDING);
    }

    @Override
    public BigDecimal calculateNetProfitMargin(BigDecimal netIncome, BigDecimal revenue) {
        return safeDivide(safe(netIncome), safe(revenue)).multiply(HUNDRED)
                .setScale(SCALE, ROUNDING);
    }

    @Override
    public BigDecimal calculateReturnOnAssets(BigDecimal netIncome, BigDecimal totalAssets) {
        return safeDivide(safe(netIncome), safe(totalAssets)).multiply(HUNDRED)
                .setScale(SCALE, ROUNDING);
    }

    @Override
    public BigDecimal calculateReturnOnEquity(BigDecimal netIncome, BigDecimal totalEquity) {
        return safeDivide(safe(netIncome), safe(totalEquity)).multiply(HUNDRED)
                .setScale(SCALE, ROUNDING);
    }

    private BigDecimal safeDivide(BigDecimal numerator, BigDecimal denominator) {
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return numerator.divide(denominator, SCALE, ROUNDING);
    }

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
