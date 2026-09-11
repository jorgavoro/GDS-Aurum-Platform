package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationDecision;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationReason;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.model.PermissionAction;
import com.globaldynamicssystems.aurum.identity.security.ExecutionMode;
import com.globaldynamicssystems.aurum.identity.service.ApplicationAccessService;
import com.globaldynamicssystems.aurum.identity.service.SecurityContextService;

import com.globaldynamicssystems.aurum.engine.exception.CapabilityExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultCapabilityExecutorTest {

    private CapabilityRegistry registry;
    private CapabilityExecutionValidator validator;
    private ApplicationAccessService applicationAccessService;
    private SecurityContextService securityContextService;
    private DefaultCapabilityExecutor executor;

    @BeforeEach
    void setUp() {
        registry = mock(CapabilityRegistry.class);
        validator = mock(CapabilityExecutionValidator.class);
        applicationAccessService = mock(ApplicationAccessService.class);
        securityContextService = mock(SecurityContextService.class);
        executor = new DefaultCapabilityExecutor(registry, validator, applicationAccessService, securityContextService);
    }

    private SecurityContext ctx() {
        return new SecurityContext(UUID.randomUUID(), "u", "u@t.com",
                Set.of("ACCOUNTING_SUPERVISOR"),
                Set.of("ACCOUNTING.JOURNAL_ENTRY.POST"),
                null, null);
    }

    private Capability mockCapability(String code) {
        Capability cap = mock(Capability.class);
        CapabilityDescriptor descriptor = mock(CapabilityDescriptor.class);
        when(descriptor.getEnabled()).thenReturn(true);
        when(cap.getDescriptor()).thenReturn(descriptor);
        when(cap.execute(any())).thenReturn(new CapabilityResult(code, true, null, "OK"));
        when(registry.find(code)).thenReturn(Optional.of(cap));
        return cap;
    }

    private AuthorizationResult allow() {
        return new AuthorizationResult(AuthorizationDecision.ALLOW, AuthorizationReason.AUTHORIZED,
                "OK", UUID.randomUUID(), "ACCOUNTING.JOURNAL_ENTRY.POST");
    }

    private AuthorizationResult deny() {
        return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.NO_PERMISSION,
                "DENY", UUID.randomUUID(), null);
    }

    // TEST 3: USER mode without SecurityContext → rejected
    @Test
    void userModeWithoutSecurityContextThrows() {
        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.USER);
        request.setResource("JOURNAL_ENTRY");
        request.setAction(PermissionAction.POST);

        assertThrows(CapabilityExecutionException.class, () -> executor.execute(request));
    }

    // TEST 4: SYSTEM mode without SecurityContext → allowed
    @Test
    void systemModeWithoutSecurityContextExecutes() {
        Capability cap = mockCapability("ACCOUNTING");
        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.SYSTEM);

        CapabilityResult result = executor.execute(request);

        assertNotNull(result);
        verify(cap).execute(request);
    }

    // TEST 7: ALLOW → capability executes
    @Test
    void allowDecisionExecutesCapability() {
        Capability cap = mockCapability("ACCOUNTING");
        when(applicationAccessService.authorize(any(), any(), any())).thenReturn(allow());

        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.USER);
        request.setSecurityContext(ctx());
        request.setResource("JOURNAL_ENTRY");
        request.setAction(PermissionAction.POST);

        CapabilityResult result = executor.execute(request);

        assertNotNull(result);
        verify(cap).execute(request);
    }

    // TEST 8: DENY → capability does NOT execute
    @Test
    void denyDecisionDoesNotExecuteCapability() {
        Capability cap = mockCapability("ACCOUNTING");
        when(applicationAccessService.authorize(any(), any(), any())).thenReturn(deny());

        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.USER);
        request.setSecurityContext(ctx());
        request.setResource("JOURNAL_ENTRY");
        request.setAction(PermissionAction.POST);

        assertThrows(CapabilityExecutionException.class, () -> executor.execute(request));
        verify(cap, never()).execute(any());
    }

    // TEST 9: SecurityContext is cleared after execution
    @Test
    void securityContextIsClearedAfterExecution() {
        mockCapability("ACCOUNTING");
        when(applicationAccessService.authorize(any(), any(), any())).thenReturn(allow());

        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.USER);
        request.setSecurityContext(ctx());
        request.setResource("JOURNAL_ENTRY");
        request.setAction(PermissionAction.POST);

        executor.execute(request);

        verify(securityContextService).clearContext();
    }

    // TEST 9 variant: context cleared even on DENY
    @Test
    void securityContextIsClearedEvenOnDeny() {
        mockCapability("ACCOUNTING");
        when(applicationAccessService.authorize(any(), any(), any())).thenReturn(deny());

        CapabilityRequest request = new CapabilityRequest("ACCOUNTING", Map.of());
        request.setExecutionMode(ExecutionMode.USER);
        request.setSecurityContext(ctx());
        request.setResource("JOURNAL_ENTRY");
        request.setAction(PermissionAction.POST);

        assertThrows(CapabilityExecutionException.class, () -> executor.execute(request));
        verify(securityContextService).clearContext();
    }
}
