package com.globaldynamicssystems.aurum.engine.capability;

public class CapabilityDiscoveryRequest {

    private CapabilityType capabilityType;
    private Boolean enabledOnly;

    public CapabilityDiscoveryRequest() {
    }

    public CapabilityDiscoveryRequest(CapabilityType capabilityType, Boolean enabledOnly) {
        this.capabilityType = capabilityType;
        this.enabledOnly = enabledOnly;
    }

    public CapabilityType getCapabilityType() {
        return capabilityType;
    }

    public void setCapabilityType(CapabilityType capabilityType) {
        this.capabilityType = capabilityType;
    }

    public Boolean getEnabledOnly() {
        return enabledOnly;
    }

    public void setEnabledOnly(Boolean enabledOnly) {
        this.enabledOnly = enabledOnly;
    }
}