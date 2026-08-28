package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Service;

import com.globaldynamicssystems.aurum.engine.exception.CapabilityExecutionException;

@Service
public class DefaultCapabilityExecutor implements CapabilityExecutor {

    private final CapabilityRegistry registry;
    private final CapabilityExecutionValidator validator;

    public DefaultCapabilityExecutor(CapabilityRegistry registry, CapabilityExecutionValidator validator) {
        this.registry = registry;
        this.validator = validator;
    }

    @Override
    public CapabilityResult execute(CapabilityRequest request) {
        validator.validate(request);

        String code = request.getCapabilityCode();
        Capability capability = registry.find(code)
                .orElseThrow(() -> new CapabilityExecutionException("Capability not found: " + code));

        if (capability.getDescriptor() != null && Boolean.FALSE.equals(capability.getDescriptor().getEnabled())) {
            throw new CapabilityExecutionException("Capability is disabled: " + code);
        }

        return capability.execute(request);
    }
}