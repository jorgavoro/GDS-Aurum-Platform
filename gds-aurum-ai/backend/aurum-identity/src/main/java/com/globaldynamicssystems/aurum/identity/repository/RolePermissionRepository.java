package com.globaldynamicssystems.aurum.identity.repository;

import com.globaldynamicssystems.aurum.identity.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {
    List<RolePermission> findByRoleId(UUID roleId);
    List<RolePermission> findByPermissionId(UUID permissionId);
    boolean existsByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}