package com.globaldynamicssystems.aurum.engine.accounting.impl;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryException;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapability;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityExecutor;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityRequest;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityResponse;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityType;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountingCapabilityExecutor implements AccountingCapabilityExecutor {

    private final AccountingCapability accountingCapability;

    public DefaultAccountingCapabilityExecutor(AccountingCapability accountingCapability) {
        this.accountingCapability = accountingCapability;
    }

    @Override
    public AccountingCapabilityResponse execute(AccountingCapabilityRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AccountingCapabilityRequest cannot be null");
        }
        if (request.getCapabilityType() == null) {
            throw new IllegalArgumentException("AccountingCapabilityType cannot be null");
        }
        if (request.getQuery() == null) {
            throw new IllegalArgumentException("AccountingQueryRequest cannot be null within capability request");
        }

        // Mapeo de consistencia entre Engine y Accounting Query Model
        request.getQuery().setQueryType(mapType(request.getCapabilityType()));

        try {
            var result = accountingCapability.execute(request.getQuery());
            return new AccountingCapabilityResponse(
                    request.getCapabilityType(),
                    result,
                    true,
                    "Capability execution successful"
            );
        } catch (AccountingQueryException e) {
            // Regla de error: Errores controlados de capacidad devuelven successful = false.
            // Excepciones no previstas (ej. NullPointerException) escalarán sin ser capturadas aquí.
            return new AccountingCapabilityResponse(
                    request.getCapabilityType(),
                    null,
                    false,
                    e.getMessage()
            );
        }
    }

    private AccountingQueryType mapType(AccountingCapabilityType capabilityType) {
        switch (capabilityType) {
            case ACCOUNT:
                return AccountingQueryType.ACCOUNT;
            case PROFITABILITY:
                return AccountingQueryType.PROFITABILITY;
            case RATIO:
                return AccountingQueryType.RATIO;
            case CASH_FLOW:
                return AccountingQueryType.CASH_FLOW;
            case KPI:
                return AccountingQueryType.KPI;
            case DATA_QUALITY:
                return AccountingQueryType.DATA_QUALITY;
            case COMPARISON:
                return AccountingQueryType.COMPARISON;
            default:
                throw new IllegalArgumentException("Unsupported accounting capability type: " + capabilityType);
        }
    }
}