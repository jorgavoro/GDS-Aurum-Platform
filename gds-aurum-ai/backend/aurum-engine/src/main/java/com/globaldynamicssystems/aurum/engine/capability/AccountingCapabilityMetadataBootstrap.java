package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadata;
import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadataRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

        // Registrar Metadatos de Parámetros
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("chartOfAccountsId", "Unique identifier for the Chart of Accounts");
        descriptions.put("fiscalPeriodId", "Unique identifier for the Fiscal Period");
        descriptions.put("queryType", "Type of accounting query to execute (e.g., TRIAL_BALANCE, GENERAL_LEDGER)");
        descriptions.put("accountIds", "List of account IDs to filter the query");

        Map<String, Class<?>> types = new HashMap<>();
        types.put("chartOfAccountsId", Long.class);
        types.put("fiscalPeriodId", Long.class);
        types.put("queryType", String.class);
        types.put("accountIds", java.util.List.class);

        CapabilityMetadata metadata = new CapabilityMetadata(code, descriptions, types, new HashMap<>());
        metadataRegistry.register(metadata);
    }
}