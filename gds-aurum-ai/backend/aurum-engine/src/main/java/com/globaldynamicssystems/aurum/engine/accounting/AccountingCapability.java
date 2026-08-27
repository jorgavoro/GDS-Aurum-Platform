package com.globaldynamicssystems.aurum.engine.accounting;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryRequest;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;

public interface AccountingCapability {
    AccountingQueryResult execute(AccountingQueryRequest request);
}