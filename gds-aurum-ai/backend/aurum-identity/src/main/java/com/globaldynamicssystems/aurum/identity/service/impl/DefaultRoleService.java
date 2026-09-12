package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.RoleAlreadyExistsException;
import com.globaldynamicssystems.aurum.identity.exception.RoleNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RoleStatus;
import com.globaldynamicssystems.aurum.identity.repository.RoleRepository;
import com.globaldynamicssystems.aurum.identity.service.RoleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultRoleService implements RoleService {

    private final RoleRepository roleRepository;

    public DefaultRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role create(Role role) {
        if (role.getCode() != null) {
            role.setCode(role.getCode().trim().toUpperCase());
        }
        if (role.getName() != null) {
            role.setName(role.getName().trim());
        }

        if (role.getCode() != null && roleRepository.existsByCode(role.getCode())) {
            throw new RoleAlreadyExistsException("Role with code " + role.getCode() + " already exists.");
        }

        if (role.getStatus() == null) {
            role.setStatus(RoleStatus.ACTIVE);
        }

        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(UUID id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return roleRepository.findByCode(code.trim().toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findActive() {
        return roleRepository.findByStatus(RoleStatus.ACTIVE);
    }

    @Override
    @Transactional
    public Role updateStatus(UUID roleId, RoleStatus status) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found."));
        role.setStatus(status);
        return roleRepository.save(role);
    }
}
