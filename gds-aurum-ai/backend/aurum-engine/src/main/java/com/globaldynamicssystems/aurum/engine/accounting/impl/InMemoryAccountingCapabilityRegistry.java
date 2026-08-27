package com.globaldynamicssystems.aurum.engine.accounting.impl;

import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapability;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityRegistry;
import com.globaldynamicssystems.aurum.engine.accounting.AccountingCapabilityType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class InMemoryAccountingCapabilityRegistry implements AccountingCapabilityRegistry {

    private final ConcurrentMap<AccountingCapabilityType, AccountingCapability> registry = new ConcurrentHashMap<>();

    @Override
    public void register(AccountingCapabilityType type, AccountingCapability capability) {
        if (type == null || capability == null) {
            throw new IllegalArgumentException("AccountingCapabilityType and AccountingCapability must not be null");
        }
        registry.put(type, capability);
    }

    @Override
    public Optional<AccountingCapability> find(AccountingCapabilityType type) {
        if (type == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(registry.get(type));
    }

    @Override
    public List<AccountingCapabilityType> getAvailableCapabilities() {
        return new ArrayList<>(registry.keySet());
    }
}