package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationDecision;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.security.ExecutionMode;
import com.globaldynamicssystems.aurum.identity.service.ApplicationAccessService;
import com.globaldynamicssystems.aurum.identity.service.SecurityContextService;

import org.springframework.stereotype.Service;

import com.globaldynamicssystems.aurum.engine.exception.CapabilityExecutionException;

@Service
public class DefaultCapabilityExecutor implements CapabilityExecutor {

    private final CapabilityRegistry registry;
    private final CapabilityExecutionValidator validator;
    private final ApplicationAccessService applicationAccessService;
    private final SecurityContextService securityContextService;

    public DefaultCapabilityExecutor(CapabilityRegistry registry,
                                     CapabilityExecutionValidator validator,
                                     ApplicationAccessService applicationAccessService,
                                     SecurityContextService securityContextService) {
        this.registry = registry;
        this.validator = validator;
        this.applicationAccessService = applicationAccessService;
        this.securityContextService = securityContextService;
    }

    @Override
    public CapabilityResult execute(CapabilityRequest request) {
        validator.validate(request);

        ExecutionMode mode = request.getExecutionMode() != null ? request.getExecutionMode() : ExecutionMode.USER;

        if (mode == ExecutionMode.USER) {
            SecurityContext context = request.getSecurityContext();
            if (context == null) {
                throw new CapabilityExecutionException("SecurityContext is required for USER execution mode.");
            }

            if (request.getResource() != null && request.getAction() != null) {
                securityContextService.setContext(context);
                try {
                    AuthorizationResult result = applicationAccessService.authorize(
                            request.getCapabilityCode(),
                            request.getResource(),
                            request.getAction());
                    if (result == null || result.getDecision() != AuthorizationDecision.ALLOW) {
                        throw new CapabilityExecutionException("Authorization DENY for capability: "
                                + request.getCapabilityCode());
                    }
                } finally {
                    securityContextService.clearContext();
                }
            }
        }

        String code = request.getCapabilityCode();
        Capability capability = registry.find(code)
                .orElseThrow(() -> new CapabilityExecutionException("Capability not found: " + code));

        if (capability.getDescriptor() != null && Boolean.FALSE.equals(capability.getDescriptor().getEnabled())) {
            throw new CapabilityExecutionException("Capability is disabled: " + code);
        }

        return capability.execute(request);
    }
}