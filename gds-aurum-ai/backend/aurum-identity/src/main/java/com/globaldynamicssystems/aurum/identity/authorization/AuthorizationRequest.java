package com.globaldynamicssystems.aurum.identity.authorization;

import com.globaldynamicssystems.aurum.identity.model.PermissionAction;
import com.globaldynamicssystems.aurum.identity.model.PermissionScopeType;

import java.util.Map;
import java.util.UUID;

public class AuthorizationRequest {

    private UUID userId;
    private String capability;
    private String resource;
    private PermissionAction action;
    private PermissionScopeType scopeType;
    private Map<String, Object> context;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(UUID userId, String capability, String resource, 
                                PermissionAction action, PermissionScopeType scopeType, 
                                Map<String, Object> context) {
        this.userId = userId;
        this.capability = capability;
        this.resource = resource;
        this.action = action;
        this.scopeType = scopeType;
        this.context = context;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
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

    public PermissionScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(PermissionScopeType scopeType) {
        this.scopeType = scopeType;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}