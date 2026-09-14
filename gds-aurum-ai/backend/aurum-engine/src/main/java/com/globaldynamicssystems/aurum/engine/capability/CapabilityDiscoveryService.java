package com.globaldynamicssystems.aurum.engine.capability;

import java.util.List;
import java.util.Optional;

public interface CapabilityDiscoveryService {
    
    List<CapabilityDescriptor> discover();

    Optional<CapabilityDescriptor> discover(String code);

    List<CapabilityDescriptor> discover(CapabilityDiscoveryRequest request);
}