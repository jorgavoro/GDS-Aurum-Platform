package com.globaldynamicssystems.aurum.engine.capability;

import java.util.ArrayList;
import java.util.List;

public class CapabilityDescriptor {

    private String code;
    private String name;
    private String description;
    private CapabilityType capabilityType;
    private List<String> supportedOperations;
    private List<String> requiredParameters;
    private List<String> optionalParameters;
    private Boolean enabled;

    public CapabilityDescriptor() {
        this.supportedOperations = new ArrayList<>();
        this.requiredParameters = new ArrayList<>();
        this.optionalParameters = new ArrayList<>();
    }

    public CapabilityDescriptor(String code, String name, String description, CapabilityType capabilityType,
                                List<String> supportedOperations, List<String> requiredParameters,
                                List<String> optionalParameters, Boolean enabled) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.capabilityType = capabilityType;
        this.supportedOperations = supportedOperations;
        this.requiredParameters = requiredParameters;
        this.optionalParameters = optionalParameters;
        this.enabled = enabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CapabilityType getCapabilityType() {
        return capabilityType;
    }

    public void setCapabilityType(CapabilityType capabilityType) {
        this.capabilityType = capabilityType;
    }

    public List<String> getSupportedOperations() {
        return supportedOperations;
    }

    public void setSupportedOperations(List<String> supportedOperations) {
        this.supportedOperations = supportedOperations;
    }

    public List<String> getRequiredParameters() {
        return requiredParameters;
    }

    public void setRequiredParameters(List<String> requiredParameters) {
        this.requiredParameters = requiredParameters;
    }

    public List<String> getOptionalParameters() {
        return optionalParameters;
    }

    public void setOptionalParameters(List<String> optionalParameters) {
        this.optionalParameters = optionalParameters;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}