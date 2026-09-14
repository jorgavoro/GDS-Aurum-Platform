package com.globaldynamicssystems.aurum.identity.authorization;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SecurityContextTest {

    private SecurityContext context(Set<String> roles, Set<String> permissions) {
        return new SecurityContext(UUID.randomUUID(), "user1", "user1@test.com",
                roles, permissions, null, null);
    }

    @Test
    void rolesAreImmutable() {
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        SecurityContext ctx = context(roles, Set.of());
        assertThrows(UnsupportedOperationException.class, () -> ctx.getRoles().add("NEW_ROLE"));
    }

    @Test
    void permissionsAreImmutable() {
        Set<String> perms = new HashSet<>();
        perms.add("ACCOUNTING.JOURNAL_ENTRY.POST");
        SecurityContext ctx = context(Set.of(), perms);
        assertThrows(UnsupportedOperationException.class, () -> ctx.getPermissions().add("OTHER"));
    }

    @Test
    void nullRolesDefaultsToEmpty() {
        SecurityContext ctx = context(null, null);
        assertNotNull(ctx.getRoles());
        assertTrue(ctx.getRoles().isEmpty());
    }

    @Test
    void nullPermissionsDefaultsToEmpty() {
        SecurityContext ctx = context(null, null);
        assertNotNull(ctx.getPermissions());
        assertTrue(ctx.getPermissions().isEmpty());
    }

    // TEST 10: hasPermission returns true when permission exists
    @Test
    void hasPermissionReturnsTrueWhenPresent() {
        SecurityContext ctx = context(Set.of(), Set.of("ACCOUNTING.JOURNAL_ENTRY.POST"));
        assertTrue(ctx.hasPermission("ACCOUNTING.JOURNAL_ENTRY.POST"));
    }

    @Test
    void hasPermissionIsCaseInsensitive() {
        SecurityContext ctx = context(Set.of(), Set.of("ACCOUNTING.JOURNAL_ENTRY.POST"));
        assertTrue(ctx.hasPermission("accounting.journal_entry.post"));
    }

    @Test
    void hasPermissionReturnsFalseWhenAbsent() {
        SecurityContext ctx = context(Set.of(), Set.of());
        assertFalse(ctx.hasPermission("ACCOUNTING.JOURNAL_ENTRY.POST"));
    }

    @Test
    void hasPermissionReturnsFalseForNull() {
        SecurityContext ctx = context(Set.of(), Set.of());
        assertFalse(ctx.hasPermission(null));
    }

    // TEST 11: hasRole returns true when role exists
    @Test
    void hasRoleReturnsTrueWhenPresent() {
        SecurityContext ctx = context(Set.of("ACCOUNTING_SUPERVISOR"), Set.of());
        assertTrue(ctx.hasRole("ACCOUNTING_SUPERVISOR"));
    }

    @Test
    void hasRoleIsCaseInsensitive() {
        SecurityContext ctx = context(Set.of("ACCOUNTING_SUPERVISOR"), Set.of());
        assertTrue(ctx.hasRole("accounting_supervisor"));
    }

    @Test
    void hasRoleReturnsFalseWhenAbsent() {
        SecurityContext ctx = context(Set.of(), Set.of());
        assertFalse(ctx.hasRole("ACCOUNTING_SUPERVISOR"));
    }

    @Test
    void tenantIdAndCompanyIdCanBeNull() {
        SecurityContext ctx = new SecurityContext(UUID.randomUUID(), "u", "u@t.com",
                Set.of(), Set.of(), null, null);
        assertNull(ctx.getTenantId());
        assertNull(ctx.getCompanyId());
    }
}
