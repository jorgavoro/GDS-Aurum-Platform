package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DefaultProfitabilityCalculator implements ProfitabilityCalculator {

    @Override
    public BigDecimal calculateNetResult(BigDecimal revenue, BigDecimal expense) {
        BigDecimal r = revenue != null ? revenue : BigDecimal.ZERO;
        BigDecimal e = expense != null ? expense : BigDecimal.ZERO;
        return r.subtract(e);
    }

    @Override
    public BigDecimal calculateMarginPercentage(BigDecimal revenue, BigDecimal netResult) {
        BigDecimal r = revenue != null ? revenue : BigDecimal.ZERO;
        BigDecimal n = netResult != null ? netResult : BigDecimal.ZERO;
        if (r.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return n.divide(r, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
