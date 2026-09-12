package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationDecision;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationReason;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.model.PermissionAction;
import com.globaldynamicssystems.aurum.identity.service.impl.DefaultApplicationAccessService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultApplicationAccessServiceTest {

    private SecurityContextService securityContextService;
    private AuthorizationService authorizationService;
    private DefaultApplicationAccessService service;

    @BeforeEach
    void setUp() {
        securityContextService = mock(SecurityContextService.class);
        authorizationService = mock(AuthorizationService.class);
        service = new DefaultApplicationAccessService(securityContextService, authorizationService);
    }

    private SecurityContext ctx() {
        return new SecurityContext(UUID.randomUUID(), "u", "u@t.com", Set.of(), Set.of(), null, null);
    }

    private AuthorizationResult allow() {
        return new AuthorizationResult(AuthorizationDecision.ALLOW, AuthorizationReason.AUTHORIZED,
                "OK", UUID.randomUUID(), "ACCOUNTING.JOURNAL_ENTRY.POST");
    }

    private AuthorizationResult deny() {
        return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.NO_PERMISSION,
                "DENY", UUID.randomUUID(), null);
    }

    // TEST 5: SecurityContext with ACCOUNTING.JOURNAL_ENTRY.POST → ALLOW
    @Test
    void canAccessReturnsTrueOnAllow() {
        when(securityContextService.requireContext()).thenReturn(ctx());
        when(authorizationService.authorize(any())).thenReturn(allow());

        assertTrue(service.canAccess("ACCOUNTING", "JOURNAL_ENTRY", PermissionAction.POST));
    }

    // TEST 6: SecurityContext without ACCOUNTING.JOURNAL_ENTRY.POST → DENY
    @Test
    void canAccessReturnsFalseOnDeny() {
        when(securityContextService.requireContext()).thenReturn(ctx());
        when(authorizationService.authorize(any())).thenReturn(deny());

        assertFalse(service.canAccess("ACCOUNTING", "JOURNAL_ENTRY", PermissionAction.POST));
    }

    // DENY does not throw exception
    @Test
    void authorizeReturnsDenyWithoutThrowingException() {
        when(securityContextService.requireContext()).thenReturn(ctx());
        when(authorizationService.authorize(any())).thenReturn(deny());

        AuthorizationResult result = assertDoesNotThrow(
                () -> service.authorize("ACCOUNTING", "JOURNAL_ENTRY", PermissionAction.POST));
        assertEquals(AuthorizationDecision.DENY, result.getDecision());
    }

    @Test
    void authorizeDelegatesToAuthorizationService() {
        when(securityContextService.requireContext()).thenReturn(ctx());
        when(authorizationService.authorize(any())).thenReturn(allow());

        service.authorize("ACCOUNTING", "JOURNAL_ENTRY", PermissionAction.POST);

        verify(authorizationService).authorize(any());
    }
}
