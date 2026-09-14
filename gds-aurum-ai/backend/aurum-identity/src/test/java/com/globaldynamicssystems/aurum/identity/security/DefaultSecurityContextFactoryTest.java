package com.globaldynamicssystems.aurum.identity.security;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.exception.InvalidSecurityContextException;
import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserRole;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.RolePermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRoleRepository;
import com.globaldynamicssystems.aurum.identity.security.impl.DefaultSecurityContextFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultSecurityContextFactoryTest {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RolePermissionRepository rolePermissionRepository;
    private DefaultSecurityContextFactory factory;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);
        rolePermissionRepository = mock(RolePermissionRepository.class);
        factory = new DefaultSecurityContextFactory(userRepository, userRoleRepository, rolePermissionRepository);
    }

    private User activeUser(UUID id) {
        User user = new User("user1", "user1@test.com", "First", "Last", UserStatus.ACTIVE, true);
        // set id via reflection is not allowed per rules; use a spy approach
        User spy = spy(user);
        doReturn(id).when(spy).getId();
        return spy;
    }

    // TEST 1: User ACTIVE generates valid SecurityContext
    @Test
    void activeUserGeneratesSecurityContext() {
        UUID userId = UUID.randomUUID();
        User user = activeUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of());

        SecurityContext ctx = factory.create(userId);

        assertNotNull(ctx);
        assertEquals("user1", ctx.getUsername());
        assertEquals("user1@test.com", ctx.getEmail());
    }

    // TEST 2: User DISABLED throws InvalidSecurityContextException
    @ParameterizedTest
    @EnumSource(value = UserStatus.class, names = {"DISABLED", "LOCKED", "DELETED", "PENDING"})
    void nonActiveUserThrowsInvalidSecurityContextException(UserStatus status) {
        UUID userId = UUID.randomUUID();
        User user = new User("user1", "user1@test.com", "First", "Last", status, false);
        User spy = spy(user);
        doReturn(userId).when(spy).getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(spy));

        assertThrows(InvalidSecurityContextException.class, () -> factory.create(userId));
    }

    @Test
    void unknownUserThrowsUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> factory.create(userId));
    }

    @Test
    void rolesAreCollectedFromUserRoles() {
        UUID userId = UUID.randomUUID();
        User user = activeUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Role role = new Role();
        role.setCode("ACCOUNTING_SUPERVISOR");
        UserRole userRole = new UserRole();
        userRole.setRole(role);

        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(userRole));
        when(rolePermissionRepository.findByRoleId(any())).thenReturn(List.of());

        SecurityContext ctx = factory.create(userId);

        assertTrue(ctx.hasRole("ACCOUNTING_SUPERVISOR"));
    }
}
