package com.globaldynamicssystems.aurum.identity.authorization.impl;

/**
 * @deprecated Use {@link com.globaldynamicssystems.aurum.identity.security.impl.DefaultSecurityContextFactory} instead.
 * This class is retained only to avoid breaking existing references during migration.
 */
@Deprecated
public class DefaultSecurityContextFactory extends com.globaldynamicssystems.aurum.identity.security.impl.DefaultSecurityContextFactory {

    public DefaultSecurityContextFactory(
            com.globaldynamicssystems.aurum.identity.repository.UserRepository userRepository,
            com.globaldynamicssystems.aurum.identity.repository.UserRoleRepository userRoleRepository,
            com.globaldynamicssystems.aurum.identity.repository.RolePermissionRepository rolePermissionRepository) {
        super(userRepository, userRoleRepository, rolePermissionRepository);
    }
}
