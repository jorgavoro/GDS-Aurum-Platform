package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCapabilityRegistry implements CapabilityRegistry {

    private final ConcurrentHashMap<String, Capability> registry = new ConcurrentHashMap<>();

    @Override
    public void register(Capability capability) {
        if (capability != null && capability.getCode() != null) {
            registry.put(capability.getCode(), capability);
        }
    }

    @Override
    public Optional<Capability> find(String capabilityCode) {
        if (capabilityCode == null || capabilityCode.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(registry.get(capabilityCode));
    }

    @Override
    public List<Capability> findAll() {
        return new ArrayList<>(registry.values());
    }

    @Override
    public boolean exists(String capabilityCode) {
        if (capabilityCode == null || capabilityCode.trim().isEmpty()) {
            return false;
        }
        return registry.containsKey(capabilityCode);
    }

    @Override
    public void unregister(String capabilityCode) {
        if (capabilityCode != null && !capabilityCode.trim().isEmpty()) {
            registry.remove(capabilityCode);
        }
    }
}