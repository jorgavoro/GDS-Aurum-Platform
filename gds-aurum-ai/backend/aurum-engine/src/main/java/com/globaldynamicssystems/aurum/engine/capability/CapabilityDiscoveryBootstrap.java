package com.globaldynamicssystems.aurum.engine.capability;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CapabilityDiscoveryBootstrap {

    private final CapabilityDiscoveryRegistry registry;
    private final AccountingCapabilityDescriptorFactory factory;

    public CapabilityDiscoveryBootstrap(CapabilityDiscoveryRegistry registry, AccountingCapabilityDescriptorFactory factory) {
        this.registry = registry;
        this.factory = factory;
    }

    @PostConstruct
    public void initialize() {
        CapabilityDescriptor accountingDescriptor = factory.create();
        registry.register(accountingDescriptor);
    }
}