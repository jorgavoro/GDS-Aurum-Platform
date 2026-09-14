package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceLine;

import java.math.BigDecimal;
import java.util.List;

public interface OpeningBalanceCalculator {

    List<OpeningBalanceLine> calculate(
            Long chartOfAccountsId,
            Integer sourceFiscalYear
    );

    BigDecimal calculateDebitTotal(
            List<OpeningBalanceLine> lines
    );

    BigDecimal calculateCreditTotal(
            List<OpeningBalanceLine> lines
    );
}