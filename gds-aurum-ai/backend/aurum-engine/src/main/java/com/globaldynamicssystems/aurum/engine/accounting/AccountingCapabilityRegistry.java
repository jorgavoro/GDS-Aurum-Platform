package com.globaldynamicssystems.aurum.engine.accounting;

import java.util.List;
import java.util.Optional;

public interface AccountingCapabilityRegistry {
    void register(AccountingCapabilityType type, AccountingCapability capability);
    Optional<AccountingCapability> find(AccountingCapabilityType type);
    List<AccountingCapabilityType> getAvailableCapabilities();
}