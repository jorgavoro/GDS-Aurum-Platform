package com.globaldynamicssystems.aurum.accounting.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface AccountBalanceService {

    BigDecimal calculateBalance(Long accountId, Long fiscalPeriodId);
}

@Service
class DefaultAccountBalanceService implements AccountBalanceService {

    private final LedgerService ledgerService;

    public DefaultAccountBalanceService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Override
    public BigDecimal calculateBalance(Long accountId, Long fiscalPeriodId) {
        if (accountId == null || fiscalPeriodId == null) {
            return BigDecimal.ZERO;
        }
        return ledgerService.calculateBalance(accountId, fiscalPeriodId);
    }
}