package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Component;

import com.globaldynamicssystems.aurum.engine.exception.CapabilityExecutionException;

@Component
public class DefaultCapabilityExecutionValidator implements CapabilityExecutionValidator {

    @Override
    public void validate(CapabilityRequest request) {
        if (request == null) {
            throw new CapabilityExecutionException("CapabilityRequest cannot be null");
        }
        if (request.getCapabilityCode() == null || request.getCapabilityCode().trim().isEmpty()) {
            throw new CapabilityExecutionException("CapabilityCode cannot be null or empty");
        }
        if (request.getParameters() == null) {
            throw new CapabilityExecutionException("Parameters map cannot be null");
        }
    }
}