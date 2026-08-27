package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Component;

@Component
public class DefaultCapabilityDiscoveryValidator implements CapabilityDiscoveryValidator {

    @Override
    public void validate(CapabilityDiscoveryRequest request) {
        if (request == null) {
            return;
        }
        
        // Validación puramente estructural: si existe capabilityType, no debe romper el contrato
        if (request.getCapabilityType() != null) {
            // El propio sistema de tipos estático de Java protege contra valores inválidos de Enum.
            // Aquí podrían añadirse validaciones futuras de formato si el DTO lo requiere, 
            // pero NO se consulta la base de datos ni el Registry.
        }
    }
}