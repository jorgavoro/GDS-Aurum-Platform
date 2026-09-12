package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.FinancialComparisonCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DefaultFinancialComparisonCalculator implements FinancialComparisonCalculator {

    @Override
    public BigDecimal calculateVariation(BigDecimal current, BigDecimal previous) {
        BigDecimal c = current != null ? current : BigDecimal.ZERO;
        BigDecimal p = previous != null ? previous : BigDecimal.ZERO;
        return c.subtract(p);
    }

    @Override
    public BigDecimal calculateVariationPercentage(BigDecimal current, BigDecimal previous) {
        BigDecimal p = previous != null ? previous : BigDecimal.ZERO;
        if (p.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal c = current != null ? current : BigDecimal.ZERO;
        return c.subtract(p)
                .divide(p, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
