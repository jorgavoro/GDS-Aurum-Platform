package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.model.PermissionAction;
import com.globaldynamicssystems.aurum.identity.security.ExecutionMode;

import java.util.HashMap;
import java.util.Map;

public class CapabilityRequest {

    private String capabilityCode;
    private Map<String, Object> parameters;
    private SecurityContext securityContext;
    private ExecutionMode executionMode;
    private String resource;
    private PermissionAction action;

    public CapabilityRequest() {
        this.parameters = new HashMap<>();
        this.executionMode = ExecutionMode.USER;
    }

    public CapabilityRequest(String capabilityCode, Map<String, Object> parameters) {
        this.capabilityCode = capabilityCode;
        this.parameters = parameters != null ? parameters : new HashMap<>();
        this.executionMode = ExecutionMode.USER;
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

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(ExecutionMode executionMode) {
        this.executionMode = executionMode != null ? executionMode : ExecutionMode.USER;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public PermissionAction getAction() {
        return action;
    }

    public void setAction(PermissionAction action) {
        this.action = action;
    }
}