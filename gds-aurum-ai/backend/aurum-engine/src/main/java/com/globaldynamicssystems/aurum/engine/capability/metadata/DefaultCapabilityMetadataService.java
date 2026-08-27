package com.globaldynamicssystems.aurum.engine.capability.metadata;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCapabilityMetadataService implements CapabilityMetadataService {

    private final CapabilityMetadataRegistry registry;

    public DefaultCapabilityMetadataService(CapabilityMetadataRegistry registry) {
        this.registry = registry;
    }

    @Override
    public List<CapabilityMetadata> discoverMetadata() {
        return registry.findAll();
    }

    @Override
    public Optional<CapabilityMetadata> getMetadata(String capabilityCode) {
        if (capabilityCode == null || capabilityCode.trim().isEmpty()) {
            return Optional.empty();
        }
        return registry.findByCode(capabilityCode);
    }

    @Override
    public List<CapabilityMetadata> findByDomain(CapabilityDomain domain) {
        if (domain == null) {
            return List.of();
        }
        return registry.findByDomain(domain);
    }
}