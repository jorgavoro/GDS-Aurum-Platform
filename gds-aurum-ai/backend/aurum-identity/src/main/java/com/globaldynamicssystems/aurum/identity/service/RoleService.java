package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RoleStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    Role create(Role role);
    Optional<Role> findById(UUID id);
    Optional<Role> findByCode(String code);
    List<Role> findActive();
    Role updateStatus(UUID roleId, RoleStatus status);
}