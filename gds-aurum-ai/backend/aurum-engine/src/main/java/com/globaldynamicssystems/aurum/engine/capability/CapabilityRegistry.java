package com.globaldynamicssystems.aurum.engine.capability;

import java.util.List;
import java.util.Optional;

public interface CapabilityRegistry {

    void register(Capability capability);

    Optional<Capability> find(String capabilityCode);

    List<Capability> findAll();

    boolean exists(String capabilityCode);

    void unregister(String capabilityCode);
}