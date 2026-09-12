package com.globaldynamicssystems.aurum.engine.capability.metadata;

import java.util.List;
import java.util.Optional;

public interface CapabilityMetadataService {

    List<CapabilityMetadata> discoverMetadata();

    Optional<CapabilityMetadata> getMetadata(String capabilityCode);

    List<CapabilityMetadata> findByDomain(CapabilityDomain domain);
}