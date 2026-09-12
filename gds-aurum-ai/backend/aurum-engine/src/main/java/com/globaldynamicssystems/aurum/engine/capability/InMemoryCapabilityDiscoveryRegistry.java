package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCapabilityDiscoveryRegistry implements CapabilityDiscoveryRegistry {

    private final ConcurrentHashMap<String, CapabilityDescriptor> registry = new ConcurrentHashMap<>();

    @Override
    public void register(CapabilityDescriptor descriptor) {
        if (descriptor != null && descriptor.getCode() != null) {
            registry.put(descriptor.getCode(), descriptor);
        }
    }

    @Override
    public Optional<CapabilityDescriptor> find(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(registry.get(code));
    }

    @Override
    public List<CapabilityDescriptor> findAll() {
        return new ArrayList<>(registry.values());
    }

    @Override
    public boolean exists(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        return registry.containsKey(code);
    }

    @Override
    public void unregister(String code) {
        if (code != null && !code.trim().isEmpty()) {
            registry.remove(code);
        }
    }
}