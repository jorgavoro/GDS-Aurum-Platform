package com.globaldynamicssystems.aurum.accounting.service;

import java.math.BigDecimal;

public interface ProfitabilityCalculator {

    BigDecimal calculateNetResult(BigDecimal revenue, BigDecimal expense);

    BigDecimal calculateMarginPercentage(BigDecimal revenue, BigDecimal netResult);
}
