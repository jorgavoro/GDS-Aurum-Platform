package com.globaldynamicssystems.aurum.engine.capability;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CapabilityBootstrap {

    private final CapabilityRegistrationService registrationService;
    private final AccountingCapabilityAdapter accountingCapabilityAdapter;

    public CapabilityBootstrap(CapabilityRegistrationService registrationService,
                               AccountingCapabilityAdapter accountingCapabilityAdapter) {
        this.registrationService = registrationService;
        this.accountingCapabilityAdapter = accountingCapabilityAdapter;
    }

    @PostConstruct
    public void initialize() {
        registrationService.register(accountingCapabilityAdapter);
    }
}