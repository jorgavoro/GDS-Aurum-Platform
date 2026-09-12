package com.globaldynamicssystems.aurum.engine.capability.metadata;

public class CapabilityOutputMetadata {

    private String name;
    private String description;
    private CapabilityOutputType type;
    private String semanticType;
    private Boolean collection;

    public CapabilityOutputMetadata() {
    }

    public CapabilityOutputMetadata(String name, String description, CapabilityOutputType type,
                                    String semanticType, Boolean collection) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.semanticType = semanticType;
        this.collection = collection;
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

    public CapabilityOutputType getType() {
        return type;
    }

    public void setType(CapabilityOutputType type) {
        this.type = type;
    }

    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }
}