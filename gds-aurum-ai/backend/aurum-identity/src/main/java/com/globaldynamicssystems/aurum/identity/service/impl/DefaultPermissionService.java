package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.InvalidPermissionException;
import com.globaldynamicssystems.aurum.identity.exception.PermissionAlreadyExistsException;
import com.globaldynamicssystems.aurum.identity.model.Permission;
import com.globaldynamicssystems.aurum.identity.repository.PermissionRepository;
import com.globaldynamicssystems.aurum.identity.service.PermissionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPermissionService implements PermissionService {

    private final PermissionRepository permissionRepository;

    public DefaultPermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public Permission create(Permission permission) {
        if (permission.getCapability() == null || permission.getCapability().trim().isEmpty()) {
            throw new InvalidPermissionException("Capability cannot be empty.");
        }
        if (permission.getResource() == null || permission.getResource().trim().isEmpty()) {
            throw new InvalidPermissionException("Resource cannot be empty.");
        }
        if (permission.getAction() == null) {
            throw new InvalidPermissionException("Action cannot be null.");
        }
        if (permission.getScopeType() == null) {
            throw new InvalidPermissionException("ScopeType cannot be null.");
        }

        permission.setCapability(permission.getCapability().trim().toUpperCase());
        permission.setResource(permission.getResource().trim().toUpperCase());

        String generatedCode = permission.getCapability() + "." + permission.getResource() + "." + permission.getAction().name();
        permission.setCode(generatedCode);

        if (permissionRepository.existsByCode(generatedCode)) {
            throw new PermissionAlreadyExistsException("Permission with code " + generatedCode + " already exists.");
        }

        if (permission.getEnabled() == null) {
            permission.setEnabled(true);
        }

        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findById(UUID id) {
        return permissionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return permissionRepository.findByCode(code.trim().toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findByCapability(String capability) {
        if (capability == null) {
            return List.of();
        }
        return permissionRepository.findByCapability(capability.trim().toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findByCapabilityAndResource(String capability, String resource) {
        if (capability == null || resource == null) {
            return List.of();
        }
        return permissionRepository.findByCapabilityAndResource(capability.trim().toUpperCase(), resource.trim().toUpperCase());
    }
}
