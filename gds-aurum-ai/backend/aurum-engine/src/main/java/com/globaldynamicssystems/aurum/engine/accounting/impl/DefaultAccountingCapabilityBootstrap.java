package com.globaldynamicssystems.aurum.engine.accounting.impl;

import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapability;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityRegistry;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DefaultAccountingCapabilityBootstrap {

    private final AccountingCapabilityRegistry registry;
    private final AccountingCapability accountingCapability;

    public DefaultAccountingCapabilityBootstrap(AccountingCapabilityRegistry registry, 
                                                AccountingCapability accountingCapability) {
        this.registry = registry;
        this.accountingCapability = accountingCapability;
    }

    @PostConstruct
    public void bootstrap() {
        // Registramos la capacidad contable subyacente para todos los tipos de operaciones posibles.
        // Se evita crear un bean separado por cada tipo de reporte, pues AccountingCapability 
        // ya representa un punto de integración unificado y thread-safe.
        for (AccountingCapabilityType type : AccountingCapabilityType.values()) {
            registry.register(type, accountingCapability);
        }
    }
}