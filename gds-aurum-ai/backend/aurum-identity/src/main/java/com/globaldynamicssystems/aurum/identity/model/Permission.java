package com.globaldynamicssystems.aurum.identity.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "gds_permission", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"capability", "resource", "action", "scopeType"})
})
public class Permission extends AuditableEntity {

    @Column(nullable = false, unique = true, length = 150)
    private String code;

    @Column(nullable = false, length = 100)
    private String capability;

    @Column(nullable = false, length = 100)
    private String resource;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionAction action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionScopeType scopeType;

    @Column(length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean enabled = true;

    public Permission() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}