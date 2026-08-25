package com.globaldynamicssystems.aurum.accounting.service;

import java.math.BigDecimal;

public interface FinancialComparisonCalculator {

    BigDecimal calculateVariation(BigDecimal current, BigDecimal previous);

    BigDecimal calculateVariationPercentage(BigDecimal current, BigDecimal previous);
}
