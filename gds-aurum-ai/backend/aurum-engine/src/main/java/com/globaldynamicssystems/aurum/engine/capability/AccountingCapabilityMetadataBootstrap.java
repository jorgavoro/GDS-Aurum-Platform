package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadata;
import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadataRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountingCapabilityMetadataBootstrap {

    private final CapabilityDiscoveryRegistry discoveryRegistry;
    private final CapabilityMetadataRegistry metadataRegistry;

    public AccountingCapabilityMetadataBootstrap(CapabilityDiscoveryRegistry discoveryRegistry,
                                                 CapabilityMetadataRegistry metadataRegistry) {
        this.discoveryRegistry = discoveryRegistry;
        this.metadataRegistry = metadataRegistry;
    }

    @PostConstruct
    public void initialize() {
        String code = "ACCOUNTING";

        // Registrar Descriptor
        CapabilityDescriptor descriptor = new CapabilityDescriptor(
                code,
                "Accounting Query Capability",
                "Capability to perform accounting queries such as trial balances, ledger accounts, and financial statements.",
                "1.0.0",
                true
        );
        discoveryRegistry.register(descriptor);

        // Registrar Metadatos utilizando el constructor vacío y setters
        CapabilityMetadata metadata = new CapabilityMetadata();
        metadata.setCode(code);
        metadata.setName("Accounting Query Capability");
        metadata.setDescription("Capability to perform accounting queries such as trial balances, ledger accounts, and financial statements.");
        metadata.setEnabled(true);
        metadata.setInputs(new ArrayList<>());
        metadata.setConcepts(new ArrayList<>());
        metadata.setOutputs(new ArrayList<>());
        metadata.setDependencies(new ArrayList<>());

        metadataRegistry.register(metadata);
    }
}