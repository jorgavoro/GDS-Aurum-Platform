package com.globaldynamicssystems.aurum.engine.accounting.impl;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryRequest;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryService;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapability;
import org.springframework.stereotype.Component;

@Component
public class AccountingCapabilityAdapter implements AccountingCapability {

    private final AccountingQueryService accountingQueryService;

    public AccountingCapabilityAdapter(AccountingQueryService accountingQueryService) {
        this.accountingQueryService = accountingQueryService;
    }

    @Override
    public AccountingQueryResult execute(AccountingQueryRequest request) {
        // Delega directamente en el servicio de consultas contables, cumpliendo con la regla de 
        // no duplicar lógica financiera y mantener el acoplamiento a través del puerto de aplicación.
        return accountingQueryService.execute(request);
    }
}