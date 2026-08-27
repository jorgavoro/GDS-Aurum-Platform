package com.globaldynamicssystems.aurum.engine.capability.metadata;

import java.util.ArrayList;
import java.util.List;

public class CapabilityMetadata {

    private String code;
    private String name;
    private String description;
    private CapabilityDomain domain;
    private Boolean enabled;
    private List<CapabilityParameterMetadata> inputs;
    private List<CapabilityConcept> concepts;
    private List<CapabilityOutputMetadata> outputs;
    private List<CapabilityDependency> dependencies;

    public CapabilityMetadata() {
        this.inputs = new ArrayList<>();
        this.concepts = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.dependencies = new ArrayList<>();
    }

    public CapabilityMetadata(String code, String name, String description, CapabilityDomain domain, Boolean enabled,
                              List<CapabilityParameterMetadata> inputs, List<CapabilityConcept> concepts,
                              List<CapabilityOutputMetadata> outputs, List<CapabilityDependency> dependencies) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.domain = domain;
        this.enabled = enabled;
        this.inputs = inputs != null ? inputs : new ArrayList<>();
        this.concepts = concepts != null ? concepts : new ArrayList<>();
        this.outputs = outputs != null ? outputs : new ArrayList<>();
        this.dependencies = dependencies != null ? dependencies : new ArrayList<>();
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

    public CapabilityDomain getDomain() {
        return domain;
    }

    public void setDomain(CapabilityDomain domain) {
        this.domain = domain;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<CapabilityParameterMetadata> getInputs() {
        return inputs;
    }

    public void setInputs(List<CapabilityParameterMetadata> inputs) {
        this.inputs = inputs;
    }

    public List<CapabilityConcept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<CapabilityConcept> concepts) {
        this.concepts = concepts;
    }

    public List<CapabilityOutputMetadata> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<CapabilityOutputMetadata> outputs) {
        this.outputs = outputs;
    }

    public List<CapabilityDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<CapabilityDependency> dependencies) {
        this.dependencies = dependencies;
    }
}