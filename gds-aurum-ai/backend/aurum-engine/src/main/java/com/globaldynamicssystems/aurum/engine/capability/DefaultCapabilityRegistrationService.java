package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Service;

@Service
public class DefaultCapabilityRegistrationService implements CapabilityRegistrationService {

    private final CapabilityRegistry registry;

    public DefaultCapabilityRegistrationService(CapabilityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void register(Capability capability) {
        if (capability != null && capability.getCode() != null) {
            registry.register(capability);
        }
    }
}