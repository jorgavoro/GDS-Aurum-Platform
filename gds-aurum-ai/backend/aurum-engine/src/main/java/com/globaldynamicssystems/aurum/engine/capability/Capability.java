package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadata;

public interface Capability {

    String getCode();

    CapabilityDescriptor getDescriptor();

    CapabilityMetadata getMetadata();

    CapabilityResult execute(CapabilityRequest request);
}