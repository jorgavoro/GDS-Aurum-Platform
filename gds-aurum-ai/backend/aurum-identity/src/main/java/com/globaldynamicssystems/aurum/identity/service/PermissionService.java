package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionService {
    Permission create(Permission permission);
    Optional<Permission> findById(UUID id);
    Optional<Permission> findByCode(String code);
    List<Permission> findByCapability(String capability);
    List<Permission> findByCapabilityAndResource(String capability, String resource);
}