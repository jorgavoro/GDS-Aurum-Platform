package com.globaldynamicssystems.aurum.engine.capability.metadata;

public class CapabilityDependency {

    private String capabilityCode;
    private String reason;
    private Boolean required;

    public CapabilityDependency() {
    }

    public CapabilityDependency(String capabilityCode, String reason, Boolean required) {
        this.capabilityCode = capabilityCode;
        this.reason = reason;
        this.required = required;
    }

    public String getCapabilityCode() {
        return capabilityCode;
    }

    public void setCapabilityCode(String capabilityCode) {
        this.capabilityCode = capabilityCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}