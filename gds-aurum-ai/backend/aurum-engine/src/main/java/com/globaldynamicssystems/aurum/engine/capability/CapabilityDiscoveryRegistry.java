package com.globaldynamicssystems.aurum.engine.capability;

import java.util.List;
import java.util.Optional;

public interface CapabilityDiscoveryRegistry {

    void register(CapabilityDescriptor descriptor);

    Optional<CapabilityDescriptor> find(String code);

    List<CapabilityDescriptor> findAll();

    boolean exists(String code);

    void unregister(String code);
}