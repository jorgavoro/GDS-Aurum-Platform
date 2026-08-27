package com.globaldynamicssystems.aurum.engine.capability;

import java.util.HashMap;
import java.util.Map;

public class CapabilityRequest {

    private String capabilityCode;
    private Map<String, Object> parameters;

    public CapabilityRequest() {
        this.parameters = new HashMap<>();
    }

    public CapabilityRequest(String capabilityCode, Map<String, Object> parameters) {
        this.capabilityCode = capabilityCode;
        this.parameters = parameters != null ? parameters : new HashMap<>();
    }

    public String getCapabilityCode() {
        return capabilityCode;
    }

    public void setCapabilityCode(String capabilityCode) {
        this.capabilityCode = capabilityCode;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters != null ? parameters : new HashMap<>();
    }
}