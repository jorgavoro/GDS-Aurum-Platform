package com.globaldynamicssystems.aurum.engine.capability.metadata;

import java.util.List;
import java.util.Optional;

public interface CapabilityMetadataRegistry {

    void register(CapabilityMetadata metadata);

    Optional<CapabilityMetadata> findByCode(String code);

    List<CapabilityMetadata> findAll();

    List<CapabilityMetadata> findByDomain(CapabilityDomain domain);

    boolean exists(String code);
}