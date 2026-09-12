package com.globaldynamicssystems.aurum.identity.repository;

import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByCode(String code);
    boolean existsByCode(String code);
    List<Role> findByStatus(RoleStatus status);
}