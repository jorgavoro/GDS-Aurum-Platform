package com.globaldynamicssystems.aurum.engine.capability.metadata;

public class CapabilityParameterMetadata {

    private String name;
    private String description;
    private CapabilityInputType type;
    private Boolean required;
    private Boolean multiple;
    private String semanticType;

    public CapabilityParameterMetadata() {
    }

    public CapabilityParameterMetadata(String name, String description, CapabilityInputType type,
                                       Boolean required, Boolean multiple, String semanticType) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
        this.multiple = multiple;
        this.semanticType = semanticType;
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

    public CapabilityInputType getType() {
        return type;
    }

    public void setType(CapabilityInputType type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }
}