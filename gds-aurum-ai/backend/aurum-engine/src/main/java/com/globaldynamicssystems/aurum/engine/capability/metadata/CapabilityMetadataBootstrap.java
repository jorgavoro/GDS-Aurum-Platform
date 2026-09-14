package com.globaldynamicssystems.aurum.engine.capability.metadata;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CapabilityMetadataBootstrap {

    private final CapabilityMetadataRegistry registry;
    private final AccountingCapabilityMetadataFactory accountingFactory;

    public CapabilityMetadataBootstrap(CapabilityMetadataRegistry registry, 
                                       AccountingCapabilityMetadataFactory accountingFactory) {
        this.registry = registry;
        this.accountingFactory = accountingFactory;
    }

    @PostConstruct
    public void initialize() {
        CapabilityMetadata accountingMetadata = accountingFactory.create();
        registry.register(accountingMetadata);
    }
}