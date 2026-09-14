package com.globaldynamicssystems.aurum.accounting.query.impl;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryRequest;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryType;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultAccountingQueryValidator implements AccountingQueryValidator {

    @Override
    public void validate(AccountingQueryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AccountingQueryRequest cannot be null");
        }
        if (request.getQueryType() == null) {
            throw new IllegalArgumentException("AccountingQueryType is required");
        }
        if (request.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("ChartOfAccountsId is required");
        }

        if (request.getQueryType() == AccountingQueryType.COMPARISON) {
            if (request.getCurrentFiscalPeriodId() == null) {
                throw new IllegalArgumentException("CurrentFiscalPeriodId is required for COMPARISON query");
            }
            if (request.getPreviousFiscalPeriodId() == null) {
                throw new IllegalArgumentException("PreviousFiscalPeriodId is required for COMPARISON query");
            }
            if (request.getDimensionType() == null) {
                throw new IllegalArgumentException("DimensionType is required for COMPARISON query");
            }
        } else {
            if (request.getFiscalPeriodId() == null) {
                throw new IllegalArgumentException("FiscalPeriodId is required for this query type");
            }
        }
    }
}