package com.globaldynamicssystems.aurum.engine.capability.metadata;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InMemoryCapabilityMetadataRegistry implements CapabilityMetadataRegistry {

    private final ConcurrentHashMap<String, CapabilityMetadata> registry = new ConcurrentHashMap<>();

    @Override
    public void register(CapabilityMetadata metadata) {
        if (metadata != null && metadata.getCode() != null) {
            registry.put(metadata.getCode(), metadata);
        }
    }

    @Override
    public Optional<CapabilityMetadata> findByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(registry.get(code));
    }

    @Override
    public List<CapabilityMetadata> findAll() {
        return new ArrayList<>(registry.values());
    }

    @Override
    public List<CapabilityMetadata> findByDomain(CapabilityDomain domain) {
        if (domain == null) {
            return new ArrayList<>();
        }
        return registry.values().stream()
                .filter(metadata -> domain.equals(metadata.getDomain()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        return registry.containsKey(code);
    }
}