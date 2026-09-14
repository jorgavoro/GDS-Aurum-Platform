package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.exception.SecurityContextNotAvailableException;
import com.globaldynamicssystems.aurum.identity.security.SecurityContextHolder;
import com.globaldynamicssystems.aurum.identity.service.impl.DefaultSecurityContextService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultSecurityContextServiceTest {

    private SecurityContextHolder holder;
    private DefaultSecurityContextService service;

    @BeforeEach
    void setUp() {
        holder = mock(SecurityContextHolder.class);
        service = new DefaultSecurityContextService(holder);
    }

    private SecurityContext ctx() {
        return new SecurityContext(UUID.randomUUID(), "u", "u@t.com", Set.of(), Set.of(), null, null);
    }

    @Test
    void setContextDelegatesToHolder() {
        SecurityContext ctx = ctx();
        service.setContext(ctx);
        verify(holder).setContext(ctx);
    }

    @Test
    void getContextDelegatesToHolder() {
        SecurityContext ctx = ctx();
        when(holder.getContext()).thenReturn(Optional.of(ctx));
        assertEquals(Optional.of(ctx), service.getContext());
    }

    @Test
    void requireContextReturnsContextWhenPresent() {
        SecurityContext ctx = ctx();
        when(holder.getContext()).thenReturn(Optional.of(ctx));
        assertEquals(ctx, service.requireContext());
    }

    // TEST 3: No SecurityContext + USER execution → exception
    @Test
    void requireContextThrowsWhenAbsent() {
        when(holder.getContext()).thenReturn(Optional.empty());
        assertThrows(SecurityContextNotAvailableException.class, () -> service.requireContext());
    }

    @Test
    void clearContextDelegatesToHolder() {
        service.clearContext();
        verify(holder).clear();
    }
}
