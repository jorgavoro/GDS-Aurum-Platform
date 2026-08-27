package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCapabilityDiscoveryService implements CapabilityDiscoveryService {

    private final CapabilityDiscoveryRegistry registry;
    private final CapabilityDiscoveryValidator validator;

    public DefaultCapabilityDiscoveryService(CapabilityDiscoveryRegistry registry, CapabilityDiscoveryValidator validator) {
        this.registry = registry;
        this.validator = validator;
    }

    @Override
    public List<CapabilityDescriptor> discover() {
        return registry.findAll().stream()
                .filter(descriptor -> Boolean.TRUE.equals(descriptor.getEnabled()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CapabilityDescriptor> discover(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Optional.empty();
        }
        return registry.find(code);
    }

    @Override
    public List<CapabilityDescriptor> discover(CapabilityDiscoveryRequest request) {
        if (request == null) {
            return discover();
        }

        validator.validate(request);

        return registry.findAll().stream()
                .filter(descriptor -> {
                    if (Boolean.TRUE.equals(request.getEnabledOnly())) {
                        return Boolean.TRUE.equals(descriptor.getEnabled());
                    }
                    return true;
                })
                .filter(descriptor -> {
                    if (request.getCapabilityType() != null) {
                        return request.getCapabilityType().equals(descriptor.getCapabilityType());
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}