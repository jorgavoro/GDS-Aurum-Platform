package com.globaldynamicssystems.aurum.engine.capability.metadata;

public class CapabilityConcept {

    private String code;
    private String name;
    private String description;
    private String semanticType;

    public CapabilityConcept() {
    }

    public CapabilityConcept(String code, String name, String description, String semanticType) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.semanticType = semanticType;
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

    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }
}